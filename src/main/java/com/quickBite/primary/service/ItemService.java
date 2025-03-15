package com.quickBite.primary.service;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.ItemDto;
import com.quickBite.primary.mapper.ItemMapper;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo.menu.Item;
import com.quickBite.security.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.quickBite.configuration.SpringBeanContext.getBean;

@Slf4j
@Service
public class ItemService extends _BaseService {

    public String save(ItemDto.CreateItem request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Item item = new Item();
        item.setVendorId(loggedInUser.getObjectId());
        item = ItemMapper.MAPPER.mapToPojo(request);
        mongoTemplate.save(item);
        return item.getId();
    }

    public void update(String id, ItemDto.UpdateItem request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Item item = findById(mongoTemplate, id);
        item = ItemMapper.MAPPER.mapToPojo(item, request);
        mongoTemplate.save(item);
    }

    public ItemDto.DetailItem get(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Item item = findById(mongoTemplate, id);
        return ItemMapper.MAPPER.mapToDetailDto(item);
    }

    public List<ItemDto.DetailItem> list(String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<Item> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, false);
        } else {
            list = findAll(mongoTemplate);
        }
        return list.stream()
                .map(item -> ItemMapper.MAPPER.mapToDetailDto(item))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Item item = findById(mongoTemplate, id);
        item.setActive(true);
        item.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(item);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Item item = findById(mongoTemplate, id);
        item.setActive(false);
        item.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(item);
    }

    public List<KeyValueDto> itemKeyValueList() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Item> itemList = findByActive(mongoTemplate, true);
        return itemList.stream().map(item ->
                        ItemMapper.MAPPER.mapToKeyValueDto(item))
                .collect(Collectors.toList());
    }

    private List<Item> findByActive(MongoTemplate mongoTemplate, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Item.class);
    }

    private List<Item> findAll(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(Item.class);
    }

    private Item findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        Item item = mongoTemplate.findOne(query, Item.class);
        if (item == null) {
            throw new BadRequestException("Item Record Not Exist.");
        }
        return item;
    }

    public List<Item> findByRestaurantId(MongoTemplate mongoTemplate, ObjectId vendorId, ObjectId restaurantId) {
        // Only Give Those Item Which Are Active | RestaurantId | VendorId
        Query query = new Query()
                // .addCriteria(Criteria.where("vendorId").is(vendorId))
                .addCriteria(Criteria.where("restaurantId").is(restaurantId))
                .addCriteria(Criteria.where("active").is(true))
                ;
        return mongoTemplate.find(query, Item.class);
    }

}