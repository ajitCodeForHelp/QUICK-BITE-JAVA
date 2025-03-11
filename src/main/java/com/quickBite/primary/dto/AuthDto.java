package com.quickBite.primary.dto;

import  com.fasterxml.jackson.annotation.JsonInclude;
import com.quickBite.primary.pojo.enums.RoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class AuthDto {

    @Setter
    @Getter
    public static class AdminLogin {
        @NotNull private String userName;
        @NotNull private String password;
    }

    @Setter
    @Getter
    public static class VendorLogin {
        @NotNull private String userName;
        @NotNull private String password;
    }


    @Setter
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserDetails {
        private String firstName;
        private String lastName;
        private String secretKey;
        private RoleEnum userType;
    }
}