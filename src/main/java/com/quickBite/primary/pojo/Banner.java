package com.quickBite.primary.pojo;

import com.quickBite.primary.pojo.enums.BannerTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(value = "banner")
public class Banner extends _BasicEntity {

    private String title;
    private String bannerImageUrl;
    private String description;
    private BannerTypeEnum bannerTypeEnum;
    private String onclickRedirectUrl;
}
