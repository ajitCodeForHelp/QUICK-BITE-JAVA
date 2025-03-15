package com.quickBite.primary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AppCodeDto {

    @Getter
    @Setter
    public static class CreateAppCode {
        private String vendorId;
        private String restaurantId;
        private String appCodeId;
    }

    @Getter
    @Setter
    public static class UpdateAppCode {
        private String vendorId;
        private String restaurantId;
        private String appCodeId;
    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }

    @Setter
    @Getter
    public static class DetailAppCode extends _BasicDto {
        private String vendorId;
        private String restaurantId;
        private String appCodeId;
    }

}
