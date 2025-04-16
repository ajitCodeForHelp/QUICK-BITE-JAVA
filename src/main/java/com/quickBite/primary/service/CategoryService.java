package com.quickBite.primary.service;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.CategoryDto;
import com.quickBite.primary.mapper.CategoryMapper;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo.menu.Category;
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
public class CategoryService extends _BaseService {

    public String save(ObjectId restaurantId, CategoryDto.CreateCategory request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = new Category();
        category.setVendorId(loggedInUser.getObjectId());
        category.setRestaurantId(restaurantId);
        category = CategoryMapper.MAPPER.mapToPojo(request);
        mongoTemplate.save(category);
        return category.getId();
    }

    public void update(ObjectId restaurantId, String id, CategoryDto.UpdateCategory request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        category = CategoryMapper.MAPPER.mapToPojo(category, request);
        mongoTemplate.save(category);
    }

    public CategoryDto.DetailCategory get(ObjectId restaurantId, String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        return CategoryMapper.MAPPER.mapToDetailDto(category);
    }

    public List<CategoryDto.DetailCategory> list(ObjectId restaurantId, String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<Category> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, restaurantId, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, restaurantId, false);
        } else {
            list = findAll(mongoTemplate, restaurantId);
        }
        return list.stream()
                .map(category -> CategoryMapper.MAPPER.mapToDetailDto(category))
                .collect(Collectors.toList());
    }

    public void activate(ObjectId restaurantId, String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        category.setActive(true);
        category.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(category);
    }

    public void inactivate(ObjectId restaurantId, String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        category.setActive(false);
        category.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(category);
    }

    public List<KeyValueDto> categoryKeyValueList(ObjectId restaurantId) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Category> categoryList = findByActive(mongoTemplate, restaurantId, true);
        return categoryList.stream().map(category ->
                        CategoryMapper.MAPPER.mapToKeyValueDto(category))
                .collect(Collectors.toList());
    }

    public List<KeyValueDto> parentCategoryKeyValueList(ObjectId restaurantId) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Category> categoryList = parentCategoryList(mongoTemplate, restaurantId);
        return categoryList.stream().map(category ->
                        CategoryMapper.MAPPER.mapToKeyValueDto(category))
                .collect(Collectors.toList());
    }

    public List<KeyValueDto> subCategoryKeyValueList(ObjectId restaurantId) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Category> categoryList = subCategoryList(mongoTemplate, restaurantId);
        return categoryList.stream().map(category ->
                        CategoryMapper.MAPPER.mapToKeyValueDto(category))
                .collect(Collectors.toList());
    }

    private List<Category> parentCategoryList(MongoTemplate mongoTemplate, ObjectId restaurantId) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(true))
                .addCriteria(Criteria.where("restaurantId").is(restaurantId))
                .addCriteria(Criteria.where("parentCategoryId").is(null));
        return mongoTemplate.find(query, Category.class);
    }

    private List<Category> subCategoryList(MongoTemplate mongoTemplate, ObjectId restaurantId) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(true))
                .addCriteria(Criteria.where("restaurantId").is(restaurantId))
                .addCriteria(Criteria.where("parentCategoryId").ne(null));
        return mongoTemplate.find(query, Category.class);
    }

    private List<Category> findByActive(MongoTemplate mongoTemplate, ObjectId restaurantId, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("restaurantId").is(restaurantId))
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Category.class);
    }

    private List<Category> findAll(MongoTemplate mongoTemplate, ObjectId restaurantId) {
        Query query = new Query()
                .addCriteria(Criteria.where("restaurantId").is(restaurantId));
        return mongoTemplate.find(query, Category.class);
    }

    private Category findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        Category category = mongoTemplate.findOne(query, Category.class);
        if (category == null) {
            throw new BadRequestException("Category Record Not Exist.");
        }
        return category;
    }

    public List<Category> findByIds(MongoTemplate mongoTemplate, ObjectId restaurantId, List<ObjectId> ids) {
        Query query = new Query()
                .addCriteria(Criteria.where("restaurantId").is(restaurantId))
                .addCriteria(Criteria.where("id").in(ids));
        return mongoTemplate.find(query, Category.class);
    }
}