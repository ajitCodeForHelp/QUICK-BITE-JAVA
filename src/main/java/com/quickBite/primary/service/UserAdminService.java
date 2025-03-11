package com.quickBite.primary.service;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.exception.BadRequestException;
import com.quickBite.primary.pojo.UserAdmin;
import com.quickBite.security.JwtUserDetailsService;
import com.quickBite.utils.TextUtils;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAdminService extends _BaseService {

    public UserAdmin findById(String id) throws BadRequestException {
        UserAdmin user = userAdminRepository.findById(new ObjectId(id)).orElse(null);
        if (user == null) {
            throw new BadRequestException("Record Not Exist.");
        }
        return user;
    }

    public UserAdmin findByUsername(String userName) throws BadRequestException {
        UserAdmin pojo = userAdminRepository.findFirstByUsername(userName);
        if (pojo == null) {
            throw new BadRequestException("Record Not Exist.");
        }
        return pojo;
    }

    public void save(UserAdmin userAdmin) {
        userAdminRepository.save(userAdmin);
    }

    public void changePassword(String oldPassword, String newPassword) throws BadRequestException {
        UserAdmin loggedInUser = (UserAdmin) SpringBeanContext.getBean(JwtUserDetailsService.class).getLoggedInUser();
        if (!loggedInUser.getPwdText().equals(oldPassword)) {
            throw new BadRequestException("OldPassword Not Matched.");
        }
        if (TextUtils.isEmpty(newPassword)) {
            throw new BadRequestException("Invalid New Password Provided.");
        }
        loggedInUser.setPwdText(newPassword);
        loggedInUser.setPwdSecure(TextUtils.getEncodedPassword(newPassword));
        userAdminRepository.save(loggedInUser);
    }

}