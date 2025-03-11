package com.quickBite.primary.controller;

import com.quickBite.primary.service.AuthService;
import com.quickBite.primary.service.BannerService;
import com.quickBite.primary.service.SettingService;
import com.quickBite.primary.service.VendorService;
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

}