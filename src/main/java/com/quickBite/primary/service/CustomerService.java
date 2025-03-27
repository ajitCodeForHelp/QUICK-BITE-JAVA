package com.quickBite.primary.service;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.AuthDto;
import com.quickBite.primary.dto.CustomerDto;
import com.quickBite.primary.mapper.CustomerMapper;
import com.quickBite.primary.pojo.Address;
import com.quickBite.primary.pojo.Customer;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo.enums.RoleEnum;
import com.quickBite.primary.pojo.enums.VerificationTypeEnum;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CustomerService extends _BaseService {

    @Autowired
    private OneTimePasswordService oneTimePasswordService;

    public Customer save(MongoTemplate mongoTemplate, Customer userCustomer) {
        return mongoTemplate.save(userCustomer);
    }

    public void validateLoginWithOtp(MongoTemplate mongoTemplate, Vendor vendor, AuthDto.CustomerOtpLogin login) throws BadRequestException {
        boolean otpVerify = oneTimePasswordService.verifyOtp(login.getMobile(), login.getOtp(), VerificationTypeEnum.Login);
        if (!otpVerify) {
            throw new BadRequestException("Otp Verification Failed");
        }
        Customer customer = findFirstByIsdCodeAndMobile(mongoTemplate, vendor.getObjectId(), login.getIsdCode(), login.getMobile());
        if (customer == null) {
            // Register This Customer
            customer = registerCustomer(mongoTemplate, vendor, login.getIsdCode(), login.getMobile());
        }
        if(customer == null){
            throw new BadRequestException("Invalid User Request");
        }
    }

    public void forgotPassword(ObjectId vendorId, AuthDto.ForgotPassword passwordDto) throws BadRequestException {
        Vendor vendor = vendorRepository.findById(vendorId).orElse(null);
        if (vendor == null) {
            throw new BadRequestException("Invalid VendorId Provided. ");
        }
        MongoTemplate mongoTemplate = getMongoTemplate(vendor.getId());
        boolean otpVerify = oneTimePasswordService.verifyOtp(passwordDto.getMobile(), passwordDto.getOtp(), VerificationTypeEnum.ForgotPassword);
        if (!otpVerify) {
            throw new BadRequestException("Password Reset Otp Verification Failed");
        }
        Customer customer = findFirstByIsdCodeAndMobile(mongoTemplate, vendor.getObjectId(), passwordDto.getIsdCode(), passwordDto.getMobile());
        if (customer == null) {
            // Register This Customer
            customer = registerCustomer(mongoTemplate, vendor, passwordDto.getIsdCode(), passwordDto.getMobile());
        }
        if(customer == null){
            throw new BadRequestException("Invalid User Request");
        }
        customer.setPwdText(passwordDto.getNewPassword());
        customer.setPwdSecure(TextUtils.getEncodedPassword(customer.getPwdText()));
        save(mongoTemplate, customer);
    }

    private Customer registerCustomer(MongoTemplate mongoTemplate, Vendor vendor, String isdCode , String mobile){
        Customer customer = new Customer();
        customer.setAdminId(vendor.getAdminId());
        customer.setVendorId(vendor.getObjectId());
        customer.setIsdCode(isdCode);
        customer.setMobile(mobile);
        customer.setUsername(mobile);
        customer.setUserType(RoleEnum.ROLE_CUSTOMER);
        customer.setUniqueKey(TextUtils.getUniqueKey());
        return save(mongoTemplate, customer);
    }


    public void updateProfile(CustomerDto.UpdateCustomer updateDto) throws BadRequestException {
        MongoTemplate mongoTemplate = null;
        Customer customer = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        customer = CustomerMapper.MAPPER.mapToPojo(customer, updateDto);
        save(mongoTemplate, customer);
    }

    public CustomerDto.DetailCustomer get() throws BadRequestException {
        Customer customer = (Customer) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        return CustomerMapper.MAPPER.mapToDetailDto(customer);
    }

    //############################################### Admin Side Api ################################

    public List<CustomerDto.DetailCustomer> listData(String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive | All
        List<Customer> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, loggedInUser.getObjectId(), true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, loggedInUser.getObjectId(), false);
        } else {
            list = findAll(mongoTemplate, loggedInUser.getObjectId());
        }
        return list.stream()
                .map(customer -> CustomerMapper.MAPPER.mapToDetailDto(customer))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Customer customer = findById(mongoTemplate, id);
        customer.setActive(true);
        customer.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(customer);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        Customer customer = findById(mongoTemplate, id);
        customer.setActive(false);
        customer.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(customer);
    }

    private List<Customer> findByActive(MongoTemplate mongoTemplate, ObjectId vendorId, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendorId))
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, Customer.class);
    }

    private List<Customer> findAll(MongoTemplate mongoTemplate, ObjectId vendorId) {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendorId));
        return mongoTemplate.find(query, Customer.class);
    }

    public Customer findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        Customer customer = mongoTemplate.findOne(query, Customer.class);
        if (customer == null) {
            throw new BadRequestException("Customer Record Not Exist.");
        }
        return customer;
    }

    private Customer findFirstByIsdCodeAndMobile(MongoTemplate mongoTemplate, ObjectId vendorId, String isdCode, String mobile) {
        Query query = new Query()
                .addCriteria(Criteria.where("vendorId").is(vendorId))
                .addCriteria(Criteria.where("isdCode").is(isdCode))
                .addCriteria(Criteria.where("mobile").is(mobile));
        return mongoTemplate.findOne(query, Customer.class);
    }

    public Customer findFirstByUsername(MongoTemplate mongoTemplate, String username) {
        Query query = new Query()
                .addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, Customer.class);
    }
}
