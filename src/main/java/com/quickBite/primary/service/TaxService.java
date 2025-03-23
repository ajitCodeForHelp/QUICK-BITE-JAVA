package com.quickBite.primary.service;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.TaxDto;
import com.quickBite.primary.mapper.TaxMapper;
import com.quickBite.primary.pojo.Tax;
import com.quickBite.primary.pojo.Vendor;
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
public class TaxService extends _BaseService {

    public String save(ObjectId restaurantId, TaxDto.CreateTax request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Tax tax = new Tax();
        tax.setVendorId(loggedInUser.getObjectId());
        tax.setRestaurantId(restaurantId);
        tax = TaxMapper.MAPPER.mapToPojo(request);
        mongoTemplate.save(tax);
        return tax.getId();
    }

    public void update(ObjectId restaurantId, String id, TaxDto.UpdateTax request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Tax tax = findById(mongoTemplate, id);
        tax = TaxMapper.MAPPER.mapToPojo(tax, request);
        mongoTemplate.save(tax);
    }

    public TaxDto.DetailTax get(ObjectId restaurantId, String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Tax tax = findById(mongoTemplate, id);
        return TaxMapper.MAPPER.mapToDetailDto(tax);
    }

    public List<TaxDto.DetailTax> list(ObjectId restaurantId, String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<Tax> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, restaurantId, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, restaurantId, false);
        } else {
            list = findAll(mongoTemplate, restaurantId);
        }
        return list.stream()
                .map(tax -> TaxMapper.MAPPER.mapToDetailDto(tax))
                .collect(Collectors.toList());
    }

    public void activate(ObjectId restaurantId, String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Tax tax = findById(mongoTemplate, id);
        tax.setActive(true);
        tax.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(tax);
    }

    public void inactivate(ObjectId restaurantId, String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Tax tax = findById(mongoTemplate, id);
        tax.setActive(false);
        tax.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(tax);
    }

    public List<KeyValueDto> taxKeyValueList(ObjectId restaurantId) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<Tax> taxList = findByRestaurantIdActiveTax(mongoTemplate, restaurantId);
        return taxList.stream().map(tax ->
                        TaxMapper.MAPPER.mapToKeyValueDto(tax))
                .collect(Collectors.toList());
    }

    private List<Tax> findByRestaurantIdActiveTax(MongoTemplate mongoTemplate, ObjectId restaurantId) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(true))
                .addCriteria(Criteria.where("restaurantId").is(restaurantId));
        return mongoTemplate.find(query, Tax.class);
    }

    private List<Tax> findByActive(MongoTemplate mongoTemplate, ObjectId restaurantId, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("restaurantId").is(restaurantId))
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Tax.class);
    }

    private List<Tax> findAll(MongoTemplate mongoTemplate, ObjectId restaurantId) {
        Query query = new Query()
                .addCriteria(Criteria.where("restaurantId").is(restaurantId));
        return mongoTemplate.find(query, Tax.class);
    }

    private Tax findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        Tax tax = mongoTemplate.findOne(query, Tax.class);
        if (tax == null) {
            throw new BadRequestException("Tax Record Not Exist.");
        }
        return tax;
    }
}