package com.quickBite.primary.dto;

import com.quickBite.primary.pojo.enums.VerificationTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    @Getter
    @Setter
    public static class GenerateOtp {
        @NotNull private String isdCode;
        @NotNull private String mobile;
        @NotNull private VerificationTypeEnum verificationType;
    }

    @Setter
    @Getter
    public static class UpdateCustomer {
        @NotNull private String firstName;
        @NotNull private String lastName;
        private String photoImageUrl;
    }
    @Setter
    @Getter
    public static class DetailCustomer {
        private String firstName;
        private String lastName;
        private String isdCode;
        private String mobile;
        private String email;
        private String photoImageUrl;
    }
}
