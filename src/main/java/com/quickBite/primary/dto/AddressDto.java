package com.quickBite.primary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {

    @Getter
    @Setter
    public static class SaveAddress {
        // In Case Order Is Delivery To Other Person
        @NotNull private String firstName;
        @NotNull private String lastName;
        @NotNull private String mobileNumber;

        @NotNull private Double latitude;
        @NotNull private Double longitude;
        private String addressLine1;
        private String addressLine2;
        private String landMark;
        private String countryTitle;
        private String stateTitle;
        private String cityTitle;
        private String pinCode;
    }

    @Getter
    @Setter
    public static class UpdateAddress {
        // In Case Order Is Delivery To Other Person
        @NotNull private String firstName;
        @NotNull private String lastName;
        @NotNull private String mobileNumber;

        @NotNull private Double latitude;
        @NotNull private Double longitude;
        private String addressLine1;
        private String addressLine2;
        private String landMark;
        private String countryTitle;
        private String stateTitle;
        private String cityTitle;
        private String pinCode;
    }

    @Getter
    @Setter
    public static class DetailAddress extends _BasicDto {
        private String firstName;
        private String lastName;
        private String mobileNumber;

        private Double latitude;
        private Double longitude;
        private String addressLine1;
        private String addressLine2;
        private String landMark;
        private String countryTitle;
        private String stateTitle;
        private String cityTitle;
        private String pinCode;
    }
}
