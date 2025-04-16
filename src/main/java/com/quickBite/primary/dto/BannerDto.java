package com.quickBite.primary.dto;

import com.quickBite.primary.pojo.enums.BannerTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BannerDto {

    @Getter
    @Setter
    public static class CreateBanner {
        private String title;
        private BannerTypeEnum bannerTypeEnum;
        private String bannerImageUrl;
        private String onclickRedirectUrl;

    }

    @Getter
    @Setter
    public static class UpdateBanner {
        private String title;
        private String bannerImageUrl;
        private BannerTypeEnum bannerTypeEnum;
        private String onclickRedirectUrl;

    }
    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }
    @Setter
    @Getter
    public static class DetailBanner extends _BasicDto {
        private String title;
        private BannerTypeEnum bannerTypeEnum;
        private String bannerImageUrl;
        private String onclickRedirectUrl;

    }

}
