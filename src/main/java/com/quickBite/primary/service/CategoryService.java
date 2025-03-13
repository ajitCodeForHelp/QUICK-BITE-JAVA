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

    public String save(CategoryDto.CreateCategory request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = new Category();
        category.setVendorId(loggedInUser.getObjectId());
        category = CategoryMapper.MAPPER.mapToPojo(request);
        mongoTemplate.save(category);
        return category.getId();
    }

    public void update(String id, CategoryDto.UpdateCategory request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        category = CategoryMapper.MAPPER.mapToPojo(category, request);
        mongoTemplate.save(category);
    }

    public CategoryDto.DetailCategory get(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        return CategoryMapper.MAPPER.mapToDetailDto(category);
    }

    public List<CategoryDto.DetailCategory> list(String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<Category> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, false);
        } else {
            list = findAll(mongoTemplate);
        }
        return list.stream()
                .map(category -> CategoryMapper.MAPPER.mapToDetailDto(category))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        category.setActive(true);
        category.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(category);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Category category = findById(mongoTemplate, id);
        category.setActive(false);
        category.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(category);
    }

    public List<KeyValueDto> categoryKeyValueList() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Category> categoryList = findByActive(mongoTemplate, true);
        return categoryList.stream().map(category ->
                CategoryMapper.MAPPER.mapToKeyValueDto(category))
                .collect(Collectors.toList());
    }

    public List<KeyValueDto> parentCategoryKeyValueList() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Category> categoryList = parentCategoryList(mongoTemplate);
        return categoryList.stream().map(category ->
                        CategoryMapper.MAPPER.mapToKeyValueDto(category))
                .collect(Collectors.toList());
    }

    public List<KeyValueDto> subCategoryKeyValueList() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Category> categoryList = subCategoryList(mongoTemplate);
        return categoryList.stream().map(category ->
                        CategoryMapper.MAPPER.mapToKeyValueDto(category))
                .collect(Collectors.toList());
    }

    private List<Category> parentCategoryList(MongoTemplate mongoTemplate) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(true))
                .addCriteria(Criteria.where("parentCategoryId").is(null));
        return mongoTemplate.find(query, Category.class);
    }

    private List<Category> subCategoryList(MongoTemplate mongoTemplate) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(true))
                .addCriteria(Criteria.where("parentCategoryId").ne(null));
        return mongoTemplate.find(query, Category.class);
    }

    private List<Category> findByActive(MongoTemplate mongoTemplate, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Category.class);
    }

    private List<Category> findAll(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(Category.class);
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