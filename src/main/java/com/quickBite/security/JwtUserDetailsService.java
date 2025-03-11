package com.quickBite.security;

import com.quickBite.configuration.SpringBeanContext;
import com.quickBite.primary.pojo._BaseUser;
import com.quickBite.primary.pojo.enums.RoleEnum;
import com.quickBite.primary.repository.UserAdminRepository;
import com.quickBite.primary.service.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    protected UserAdminRepository userAdminRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("JwtUserDetailsService.loadUserByUsername");
        return null;
    }

    public UserDetails loadUserByUsername(String userId, String userType, String uniqueKey, String brandUuid) throws UsernameNotFoundException {
        // we use the user record id as user-name across the board
        User user = null;
        _BaseUser appUser = getAppUser(userId, userType, uniqueKey);
        if (appUser != null) {
            user = new User(appUser.getUsername(), "", new ArrayList<>());
        }
        return user;
    }

    public _BaseUser getLoggedInUser() throws UsernameNotFoundException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = jwtAuthenticationToken.getTokenAttributes().get("username").toString();
        String userId = jwtAuthenticationToken.getTokenAttributes().get("u-id").toString();
        String userType = jwtAuthenticationToken.getTokenAttributes().get("u-ty").toString();
        String userToken = jwtAuthenticationToken.getTokenAttributes().get("token").toString();
        return getAppUser(userId, userType, userToken);
    }

//    public UserClient getVendorLoggedInUser() throws UsernameNotFoundException {
//        _BaseUser loggedInUser = getLoggedInUser();
//        return (UserClient) loggedInUser;
//    }

    private _BaseUser getAppUser(String userId, String userType, String uniqueKey) throws UsernameNotFoundException {
        _BaseUser user = null;
        try {
            if (userType.equals(RoleEnum.ROLE_ADMIN.toString())
                    || userType.equals(RoleEnum.ROLE_SUPER_ADMIN.toString())) {
                user = SpringBeanContext.getBean(UserAdminService.class).findById(userId);
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        if (user == null) {
            throw new UsernameNotFoundException("UnAuthorize Access.");
        }
        if (!user.isActive()) {
            throw new UsernameNotFoundException("User Is Blocked Please Contact Admin.");
        }
        return user;
    }

}