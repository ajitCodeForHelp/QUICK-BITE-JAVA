package com.quickBite.primary.service;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.RestaurantDto;
import com.quickBite.primary.mapper.RestaurantMapper;
import com.quickBite.primary.pojo.Restaurant;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.repository.PrimarySequenceRepository;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.quickBite.configuration.SpringBeanContext.getBean;

@Slf4j
@Service
public class RestaurantService extends _BaseService {

    public String save(RestaurantDto.CreateRestaurant request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        validateRestaurantLimit(mongoTemplate, loggedInUser);
        Restaurant restaurant = new Restaurant();
        restaurant.setVendorId(loggedInUser.getObjectId());
        restaurant.setSeqId(getBean(PrimarySequenceRepository.class).getNextSequenceId(Restaurant.class.getSimpleName() + "_" + loggedInUser.getSeqId()));
        restaurant.setRestaurantTitle(request.getRestaurantTitle());
        restaurant = mongoTemplate.save(restaurant);
        // Save RestaurantId In Vendor >>
        {
            List<ObjectId> restaurantIdList = loggedInUser.getRestaurantIdList();
            if (restaurantIdList == null) {
                restaurantIdList = new ArrayList<>();
            }
            restaurantIdList.add(restaurant.getObjectId());
            loggedInUser.setRestaurantIdList(restaurantIdList);
            vendorRepository.save(loggedInUser);
        }
        return restaurant.getId();
    }

    public void update(String id, RestaurantDto.UpdateRestaurant restaurantDto) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Restaurant restaurant = findById(mongoTemplate, id);
        restaurant = RestaurantMapper.MAPPER.mapToPojo(restaurant, restaurantDto);
        mongoTemplate.save(restaurant);
    }

    public RestaurantDto.DetailRestaurant get(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Restaurant restaurant = findById(mongoTemplate, id);
        return RestaurantMapper.MAPPER.mapToDetailDto(restaurant);
    }

    public List<RestaurantDto.DetailRestaurant> list(String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<Restaurant> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, false);
        } else {
            list = findAll(mongoTemplate);
        }
        return list.stream()
                .map(restaurant -> RestaurantMapper.MAPPER.mapToDetailDto(restaurant))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Restaurant restaurant = findById(mongoTemplate, id);
        restaurant.setActive(true);
        restaurant.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(restaurant);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Restaurant restaurant = findById(mongoTemplate, id);
        restaurant.setActive(false);
        restaurant.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(restaurant);
    }

    public void restaurantOnOff(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Restaurant restaurant = findById(mongoTemplate, id);
        restaurant.setOpen(!restaurant.isOpen());
        restaurant.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(restaurant);
    }

    public String getMyRestaurantId() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Restaurant> restaurantList = findByVendorId(mongoTemplate, loggedInUser.getObjectId());
        if (TextUtils.isEmpty(restaurantList)) return null;
        // return RestaurantMapper.MAPPER.mapToDetailDto(restaurant);
        return restaurantList.get(0).getId();
    }

    public List<KeyValueDto> vendorRestaurantKeyList(ObjectId vendorId) throws BadRequestException {
        Vendor vendor = vendorRepository.findById(vendorId).orElse(null);
        if (vendor == null) return null;
        MongoTemplate mongoTemplate = getMongoTemplate(vendor.getId());
        List<Restaurant> restaurantList = findByVendorId(mongoTemplate, vendor.getObjectId());
        if (TextUtils.isEmpty(restaurantList)) return null;
        return restaurantList.stream()
                .map(restaurant -> RestaurantMapper.MAPPER.mapToKeyValueDto(restaurant))
                .toList();
    }

    private List<Restaurant> findByVendorId(MongoTemplate mongoTemplate, ObjectId vendorId) {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendorId));
        return mongoTemplate.find(query, Restaurant.class);
    }

    public List<Restaurant> findRestaurantListByVendorId(MongoTemplate mongoTemplate, ObjectId vendorId) {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendorId))
                .addCriteria(Criteria.where("active").is(true));
        return mongoTemplate.find(query, Restaurant.class);
    }

    private List<Restaurant> findByActive(MongoTemplate mongoTemplate, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Restaurant.class);
    }

    private List<Restaurant> findAll(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(Restaurant.class);
    }

    private Restaurant findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        Restaurant restaurant = mongoTemplate.findOne(query, Restaurant.class);
        if (restaurant == null) {
            throw new BadRequestException("Restaurant Record Not Exist.");
        }
        return restaurant;
    }

    public void validateRestaurantLimit(MongoTemplate mongoTemplate, Vendor vendor) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendor.getObjectId()));
        long restaurantCount = mongoTemplate.count(query, Restaurant.class);
        if (restaurantCount >= vendor.getRestaurantLimit()) {
            throw new BadRequestException("Can't Create New Restaurant, Please Contact To Admin");
        }
    }

}