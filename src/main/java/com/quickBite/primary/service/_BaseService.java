package com.quickBite.primary.service;

import com.quickBite.bean.DataTableResponsePacket;
import com.quickBite.configuration.MultiMongoDBFactory;
import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.repository.BannerRepository;
import com.quickBite.primary.repository.SettingRepository;
import com.quickBite.primary.repository.UserAdminRepository;
import com.quickBite.primary.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class _BaseService {

    @Autowired protected UserAdminRepository userAdminRepository;
    @Autowired protected SettingRepository settingRepository;
    @Autowired protected BannerRepository bannerRepository;
    @Autowired protected VendorRepository vendorRepository;


    protected DataTableResponsePacket getDataTableResponsePacket(Page pageData, List data) {
        DataTableResponsePacket responsePacket = new DataTableResponsePacket();
        responsePacket.setCurrentPage(pageData.getNumber());
        responsePacket.setPageSize(pageData.getPageable().getPageSize());
        responsePacket.setTotalPages(pageData.getTotalPages());
        responsePacket.setTotalItems(pageData.getTotalElements());
        responsePacket.setData(data);
        return responsePacket;
    }

    protected MongoTemplate getMongoTemplate(String vendorId) throws BadRequestException {
        return SpringBeanContext.getBean(MultiMongoDBFactory.class).getVendorDbConnection(vendorId);
    }
}
