package com.quickBite.primary.service;

import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.mapper.MenuMapper;
import com.quickBite.primary.pojo.AppCode;
import com.quickBite.primary.pojo.Restaurant;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo.menu.Category;
import com.quickBite.primary.pojo.menu.Item;
import com.quickBite.primary.pojo.menu.ItemAddOn;
import com.quickBite.primary.pojo.menu.Menu;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.quickBite.configuration.SpringBeanContext.getBean;

@Slf4j
@Service
public class MenuDataService extends _BaseService {

    public void buildMenuForAllVendor() throws BadRequestException {
        List<Vendor> vendorList = vendorRepository.findByActive(true);
        if (TextUtils.isEmpty(vendorList)) return;
        for (Vendor vendor : vendorList) {
            MongoTemplate mongoTemplate = getMongoTemplate(vendor.getId());
            List<Restaurant> restaurants = getBean(RestaurantService.class)
                    .findRestaurantListByVendorId(mongoTemplate, vendor.getObjectId());
            if(TextUtils.isEmpty(restaurants)) continue;
            restaurants.forEach(restaurant -> {
                generateMenu(mongoTemplate, vendor.getObjectId(), restaurant.getObjectId());
            });
        }
    }

    public void menuBuilder() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Restaurant> restaurants = getBean(RestaurantService.class).findRestaurantListByVendorId(mongoTemplate, loggedInUser.getObjectId());
        if (TextUtils.isEmpty(restaurants)) return;
        restaurants.forEach(restaurant -> {
            generateMenu(mongoTemplate, loggedInUser.getObjectId(), restaurant.getObjectId());
        });
    }

    private Menu generateMenu(MongoTemplate mongoTemplate, ObjectId vendorId, ObjectId restaurantId) {
        Menu menu = findByRestaurantId(mongoTemplate, vendorId, restaurantId);
        if (menu == null) {
            menu = new Menu();
            menu.setVendorId((vendorId));
            menu.setRestaurantId((restaurantId));
        }
        menu.setMenuItemsListList(getRestaurantMenuItemList(mongoTemplate, vendorId, restaurantId));
        menu.setModifiedAt(LocalDateTime.now());
        menu = mongoTemplate.save(menu);
        return menu;
    }

    private List<Menu.MenuItems> getRestaurantMenuItemList(MongoTemplate mongoTemplate, ObjectId vendorId, ObjectId restaurantId) {
        List<Item> itemList = getBean(ItemService.class).findByRestaurantId(mongoTemplate, vendorId, restaurantId);
        if (TextUtils.isEmpty(itemList)) return null;

        Map<ObjectId, Menu.MenuCategory> categoryMap = new HashMap<>();
        Map<ObjectId, Menu.MenuItemAddOn> itemAddOnMap = new HashMap<>();

        Set<ObjectId> categorySet = new HashSet<>();
        Set<ObjectId> itemAddOnSet = new HashSet<>();
        itemList.forEach(item -> {
            categorySet.add(item.getParentCategoryId());
            if (item.getSubCategoryId() != null) {
                categorySet.add(item.getSubCategoryId());
            }
            if (item.getRequiredAddOnIdList() != null) {
                itemAddOnSet.addAll(item.getRequiredAddOnIdList());
            }
            if (item.getOptionalAddOnIdList() != null) {
                itemAddOnSet.addAll(item.getOptionalAddOnIdList());
            }
        });

        if (!categorySet.isEmpty()) {
            List<Category> categoryList = getBean(CategoryService.class)
                    .findByIds(mongoTemplate, restaurantId, categorySet.stream().toList());

            categoryList.forEach(category -> {
                categoryMap.put(category.getObjectId(), MenuMapper.MAPPER.mapToMenuCategory(category));
            });
        }
        if (!itemAddOnSet.isEmpty()) {
            List<ItemAddOn> itemAddOnList = getBean(ItemAddOnService.class)
                    .findByIds(mongoTemplate, restaurantId, itemAddOnSet.stream().toList());

            itemAddOnList.forEach(itemAddOn -> {
                itemAddOnMap.put(itemAddOn.getObjectId(), MenuMapper.MAPPER.mapToMenuItemAddOn(itemAddOn));
            });
        }

        List<Menu.MenuItems> menuItemList = new ArrayList<>();
        for (Item item : itemList) {
            Menu.MenuItems menuItems = MenuMapper.MAPPER.mapToMenuItems(item);
            menuItems.setParentCategory(categoryMap.getOrDefault(item.getParentCategoryId(), null));
            if (item.getSubCategoryId() != null) {
                menuItems.setSubCategory(categoryMap.getOrDefault(item.getSubCategoryId(), null));
            }
            if (item.getRequiredAddOnIdList() != null) {
                List<Menu.MenuItemAddOn> menuItemAddOnList = new ArrayList<>();
                for (ObjectId id : item.getRequiredAddOnIdList()) {
                    menuItemAddOnList.add(itemAddOnMap.getOrDefault(id, null));
                }
                menuItems.setRequiredAddOnList(menuItemAddOnList);
            }
            if (item.getOptionalAddOnIdList() != null) {
                List<Menu.MenuItemAddOn> menuItemAddOnList = new ArrayList<>();
                for (ObjectId id : item.getOptionalAddOnIdList()) {
                    menuItemAddOnList.add(itemAddOnMap.getOrDefault(id, null));
                }
                menuItems.setOptionalAddOnList(menuItemAddOnList);
            }
            menuItemList.add(menuItems);
        }
        return menuItemList;
    }

    private Menu findByRestaurantId(MongoTemplate mongoTemplate, ObjectId vendorId, ObjectId restaurantId) {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendorId))
                .addCriteria(Criteria.where("restaurantId").is(restaurantId));
        return mongoTemplate.findOne(query, Menu.class);
    }

    public List<Menu.MenuItems> getMenu(String appCodeId) throws BadRequestException {
        AppCode appCode = appCodeRepository.findFirstByAppCodeId(appCodeId);
        MongoTemplate mongoTemplate = getMongoTemplate(appCode.getVendorId().toString());
        Menu menu = findByRestaurantId(mongoTemplate, appCode.getVendorId(), appCode.getRestaurantId());
        if (menu == null) {
            menu = generateMenu(mongoTemplate, appCode.getVendorId(), appCode.getRestaurantId());
        }
        return menu.getMenuItemsListList();
    }
}