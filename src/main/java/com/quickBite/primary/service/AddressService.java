package com.quickBite.primary.service;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.AddressDto;
import com.quickBite.primary.mapper.AddressMapper;
import com.quickBite.primary.pojo.Address;
import com.quickBite.primary.pojo.Customer;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AddressService extends _BaseService {

    public String save(AddressDto.SaveAddress saveAddress) throws BadRequestException {
        Customer loggedInUser = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getVendorId().toString());
        Address address = AddressMapper.MAPPER.mapToPojo(saveAddress);
        address.setCustomerId(loggedInUser.getObjectId());
        address.setCustomerName(saveAddress.getFirstName() +" "+ saveAddress.getLastName());
        address.setCustomerContact(saveAddress.getMobileNumber());
        mongoTemplate.save(address);
        return address.getId();
    }


    public void update(String id, AddressDto.UpdateAddress updateDto) throws BadRequestException {
        Customer loggedInUser = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getVendorId().toString());
        Address address = findById(mongoTemplate, id);
        address = AddressMapper.MAPPER.mapToPojo(address, updateDto);
        mongoTemplate.save(address);
    }

    public AddressDto.DetailAddress get(String id) throws BadRequestException {
        Customer loggedInUser = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getVendorId().toString());
        Address address = findById(mongoTemplate, id);
        return AddressMapper.MAPPER.mapToDetailAddress(address);
    }

    public List<AddressDto.DetailAddress> listAllCustomerAddress(String data) throws BadRequestException {
        Customer loggedInUser = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getVendorId().toString());
        // Data >  Active | Inactive | Deleted | All
        List<Address> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, loggedInUser.getObjectId(), true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, loggedInUser.getObjectId(), false);
        } else {
            list = findAll(mongoTemplate, loggedInUser.getObjectId());
        }
        if (TextUtils.isEmpty(list)) return null;
        return list.stream()
                .map(AddressMapper.MAPPER::mapToDetailAddress).toList();
    }

    public void inactivate(String id) throws BadRequestException {
        Customer loggedInUser = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getVendorId().toString());
        Address address = findById(mongoTemplate, id);
        address.setActive(false);
        address.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(address);
    }

    private List<Address> findByActive(MongoTemplate mongoTemplate, ObjectId customerId, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("customerId").is(customerId))
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Address.class);
    }

    private List<Address> findAll(MongoTemplate mongoTemplate, ObjectId customerId) {
        Query query = new Query()
                .addCriteria(Criteria.where("customerId").is(customerId));
        return mongoTemplate.find(query, Address.class);
    }

    private Address findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        Address address = mongoTemplate.findOne(query, Address.class);
        if (address == null) {
            throw new BadRequestException("Address Record Not Exist.");
        }
        return address;
    }
}
