package com.quickBite.primary.controller;

import com.quickBite.configuration.MultiMongoDBFactory;
import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class _BaseController {

    @Autowired protected AuthService authService;
    @Autowired protected SettingService settingService;
    @Autowired protected BannerService bannerService;
    @Autowired protected VendorService vendorService;
    @Autowired protected RestaurantService restaurantService;
    @Autowired protected CategoryService categoryService;
    @Autowired protected ItemAddOnService itemAddOnService;
    @Autowired protected ItemService itemService;
    @Autowired protected CustomerService customerService;
    @Autowired protected CouponCodeService couponCodeService;
    @Autowired protected OneTimePasswordService oneTimePasswordService;
    @Autowired protected MenuDataService menuDataService;
    @Autowired protected AppCodeService appCodeService;

    protected MongoTemplate getMongoTemplate(String vendorId) throws BadRequestException {
        return SpringBeanContext.getBean(MultiMongoDBFactory.class).getVendorDbConnection(vendorId);
    }

}