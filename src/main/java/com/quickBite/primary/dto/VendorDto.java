package com.quickBite.primary.dto;

import com.quickBite.primary.pojo.Vendor;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VendorDto {

    @Getter
    @Setter
    public static class CreateVendor {
        @NotNull
        @NotEmpty(message = "first name Name must not be empty")
        @Size(max = 20, message = "first name must be under 20")
        private String firstName;
        @NotNull
        @NotEmpty(message = "last name Name must not be empty")
        @Size(max = 20, message = "last name must be under 20")
        private String lastName;
        @NotNull
        @NotEmpty(message = "username must not be empty")
        private String username;
        @NotNull
        @NotEmpty(message = "password must not be empty")
        private String password;

        private String photoImageUrl;

        private Vendor.ContactDetail contactDetail;
    }

    @Getter
    @Setter
    public static class UpdateVendor {
        private String firstName;
        private String lastName;
        private String photoImageUrl;

        private Vendor.ContactDetail contactDetail;
    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }
    @Setter
    @Getter
    public static class DetailVendor extends _BasicDto {
        private String firstName;
        private String lastName;
        private String username;
        private String photoImageUrl;

        private Vendor.ContactDetail contactDetail;
    }
}
