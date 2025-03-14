package com.quickBite.primary.service;

import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.CouponCodeDto;
import com.quickBite.primary.mapper.CouponCodeMapper;
import com.quickBite.primary.pojo.CouponCode;
import com.quickBite.primary.pojo.Vendor;
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
import java.util.stream.Collectors;

import static com.quickBite.configuration.SpringBeanContext.getBean;

@Slf4j
@Service
public class CouponCodeService extends _BaseService {

    public String save(CouponCodeDto.CreateCouponCode request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        CouponCode couponCode = CouponCodeMapper.MAPPER.mapToPojo(request);
        couponCode.setVendorId(loggedInUser.getObjectId());
        couponCode.setCouponCode(TextUtils.get7CharRandomCode());
        couponCode = mongoTemplate.save(couponCode);
        return couponCode.getId();
    }

    public void update(String id, CouponCodeDto.UpdateCouponCode request) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        CouponCode couponCode = findById(mongoTemplate, id);
        couponCode = CouponCodeMapper.MAPPER.mapToPojo(couponCode, request);
        mongoTemplate.save(couponCode);
    }

    public CouponCodeDto.DetailCouponCode get(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        CouponCode couponCode = findById(mongoTemplate, id);
        return CouponCodeMapper.MAPPER.mapToDetailDto(couponCode);
    }

    public List<CouponCodeDto.DetailCouponCode> list(String data) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        // Data >  Active | Inactive  | All
        List<CouponCode> list = null;
        if (data.equals("Active")) {
            list = findByActive(mongoTemplate, true);
        } else if (data.equals("Inactive")) {
            list = findByActive(mongoTemplate, false);
        } else {
            list = findAll(mongoTemplate);
        }
        return list.stream()
                .map(couponCode -> CouponCodeMapper.MAPPER.mapToDetailDto(couponCode))
                .collect(Collectors.toList());
    }

    public void activate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        CouponCode couponCode = findById(mongoTemplate, id);
        couponCode.setActive(true);
        couponCode.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(couponCode);
    }

    public void inactivate(String id) throws BadRequestException {
        Vendor loggedInUser = (Vendor) getBean(JwtUserDetailsService.class).getLoggedInUser();
        MongoTemplate mongoTemplate = getMongoTemplate(loggedInUser.getId());
        CouponCode couponCode = findById(mongoTemplate, id);
        couponCode.setActive(false);
        couponCode.setModifiedAt(LocalDateTime.now());
        mongoTemplate.save(couponCode);
    }

    private List<CouponCode> findByActive(MongoTemplate mongoTemplate, boolean active) {
        Query query = new Query()
                .addCriteria(Criteria.where("active").is(active));
        return mongoTemplate.find(query, CouponCode.class);
    }

    private List<CouponCode> findAll(MongoTemplate mongoTemplate) {
        return mongoTemplate.findAll(CouponCode.class);
    }

    private CouponCode findById(MongoTemplate mongoTemplate, String id) throws BadRequestException {
        Query query = new Query()
                .addCriteria(Criteria.where("id").is(new ObjectId(id)));
        CouponCode category = mongoTemplate.findOne(query, CouponCode.class);
        if (category == null) {
            throw new BadRequestException("CouponCode Record Not Exist.");
        }
        return category;
    }
}
