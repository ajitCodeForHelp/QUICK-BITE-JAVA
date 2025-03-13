package com.quickBite.primary.service;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.dto.AuthDto;
import com.quickBite.primary.pojo.UserAdmin;
import com.quickBite.primary.pojo.Vendor;
import com.quickBite.primary.pojo._BaseUser;
import com.quickBite.security.JwtTokenUtil;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class AuthService extends _BaseService {
    @Autowired
    protected JwtTokenUtil jwtTokenUtil;

    public UserAdmin findByUsername(String userName) throws BadRequestException {
        UserAdmin pojo = userAdminRepository.findFirstByUsername(userName);
        if (pojo == null) {
            throw new BadRequestException("ecommerce.common.message.record_not_exist");
        }
        return pojo;
    }

//    public Customer findByCustomerUsername(String username) throws BadRequestException {
//        Customer customer = customerRepository.findFirstByUsername(username);
//        if (customer == null) {
//            throw new BadRequestException("ecommerce.common.message.record_not_exist");
//        }
//        return customer;
//    }

    public AuthDto.UserDetails loginAdmin(String userName, String password, String ipAddress) throws BadRequestException {
        UserAdmin userAdmin = findByUsername(userName);
        validateUser(userAdmin, password);
        userAdmin.setLastLogin(LocalDateTime.now());
        userAdminRepository.save(userAdmin);
        return generateAuthTokenAndGetUserDetails(userAdmin, ipAddress);
    }

    public AuthDto.UserDetails loginVendor(String userName, String password, String ipAddress) throws BadRequestException {
        Vendor vendor = SpringBeanContext.getBean(VendorService.class).findByUsername(userName);
        validateUser(vendor, password);
        vendor.setLastLogin(LocalDateTime.now());
        vendorRepository.save(vendor);
        return generateAuthTokenAndGetUserDetails(vendor, ipAddress);
    }

//    public AuthDto.UserDetails loginCustomer(String userName, String password, String ipAddress) throws BadRequestException {
//        Customer userCustomer = findByCustomerUsername(userName);
//        validateUser(userCustomer, password);
//        userCustomer.setLastLogin(LocalDateTime.now());
//        customerRepository.save(userCustomer);
//        return generateAuthTokenAndGetUserDetails(userCustomer, ipAddress);
//    }
//
//    public AuthDto.UserDetails loginOtpCustomer(AuthDto.CustomerOtpLogin login, String ipAddress) throws BadRequestException {
//        // Verify Otp And Save Customer If Not Exist And Validate.
//        customerService.validateLoginWithOtp(login);
//        Customer userCustomer = findByCustomerUsername(login.getMobile());
//        if (!userCustomer.isActive() || userCustomer.isDeleted()) {
//            throw new BadRequestException("ecommerce.common.message.user_disable");
//        }
//        userCustomer.setLastLogin(LocalDateTime.now());
//        customerRepository.save(userCustomer);
//        return generateAuthTokenAndGetUserDetails(userCustomer, ipAddress);
//    }

    private void validateUser(_BaseUser user, String password) throws BadRequestException {

        if (!TextUtils.matchPassword(password, user.getPwdSecure())) {
            throw new BadRequestException("Invalid Credential");
        }
        // We need to implement Multi Device Login as well.
        if (TextUtils.isEmpty(user.getUniqueKey())) {
            user.setUniqueKey(TextUtils.getUniqueKey());
        }
    }

    private AuthDto.UserDetails generateAuthTokenAndGetUserDetails(_BaseUser user, String ipAddress) {
        // Replace With Mapper.
        AuthDto.UserDetails adminDetail = new AuthDto.UserDetails();
        adminDetail.setFirstName((user.getFirstName() != null) ? user.getFirstName() : user.getMobile());
        adminDetail.setLastName(user.getLastName());
        adminDetail.setUserType(user.getUserType());

        String token = TextUtils.md5encryption(user.getUniqueKey()) + TextUtils.md5encryption(ipAddress);
        Map<String, Object> bodyPart = new LinkedHashMap<>();
        bodyPart.put("username", user.getUsername());
        bodyPart.put("u-id", user.getId());
        bodyPart.put("u-ty", user.getUserType().toString());
        bodyPart.put("token", token);
        adminDetail.setSecretKey(jwtTokenUtil.generateToken(bodyPart, user));
        return adminDetail;
    }


}
