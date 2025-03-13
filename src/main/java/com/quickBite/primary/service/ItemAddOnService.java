package com.quickBite.primary.service;

import com.quickBite.bean.KeyValueDto;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.ItemAddOnDto;
import com.quickBite.primary.mapper.ItemAddOnMapper;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo.menu.ItemAddOn;
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
public class ItemAddOnService extends _BaseService {

    public String save(ItemAddOnDto.CreateItemAddOn request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        ItemAddOn itemAddOn = new ItemAddOn();
        itemAddOn.setVendorId(loggedInUser.getObjectId());
        itemAddOn = ItemAddOnMapper.MAPPER.mapToPojo(request);
        mongoTemplate.save(itemAddOn);
        return itemAddOn.getId();
    }

    public void update(String id, ItemAddOnDto.UpdateItemAddOn request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        ItemAddOn itemAddOn = findById(mongoTemplate, id);
        itemAddOn = ItemAddOnMapper.MAPPER.mapToPojo(itemAddOn, request);
        mongoTemplate.save(itemAddOn);
    }

    public ItemAddOnDto.DetailItemAddOn get(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        ItemAddOn itemAddOn = findById(mongoTemplate, id);
        return ItemAddOnMapper.MAPPER.mapToDetailDto(itemAddOn);
    }

    public List<ItemAddOnDto.DetailItemAddOn> list(String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<ItemAddOn> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, false);
        } else {
            list = findAll(mongoTemplate);
        }
        return list.stream()
                .map(itemAddOn -> ItemAddOnMapper.MAPPER.mapToDetailDto(itemAddOn))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        ItemAddOn itemAddOn = findById(mongoTemplate, id);
        itemAddOn.setActive(true);
        itemAddOn.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(itemAddOn);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        ItemAddOn itemAddOn = findById(mongoTemplate, id);
        itemAddOn.setActive(false);
        itemAddOn.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(itemAddOn);
    }

    public List<KeyValueDto> itemAddOnKeyValueList() throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        List<ItemAddOn> itemAddOnList = findByActive(mongoTemplate, true);
        return itemAddOnList.stream().map(itemAddOn ->
                        ItemAddOnMapper.MAPPER.mapToKeyValueDto(itemAddOn))
                .collect(Collectors.toList());
    }

    private List<ItemAddOn> findByActive(MongoTemplate mongoTemplate, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, ItemAddOn.class);
    }

    private List<ItemAddOn> findAll(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(ItemAddOn.class);
    }

    private ItemAddOn findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        ItemAddOn itemAddOn = mongoTemplate.findOne(query, ItemAddOn.class);
        if (itemAddOn == null) {
            throw new BadRequestException("ItemAddOn Record Not Exist.");
        }
        return itemAddOn;
    }

}