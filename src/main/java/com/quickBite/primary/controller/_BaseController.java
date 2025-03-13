package com.quickBite.primary.controller;

import com.quickBite.primary.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

}