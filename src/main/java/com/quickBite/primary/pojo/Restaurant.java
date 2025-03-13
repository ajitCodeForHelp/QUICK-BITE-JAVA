package com.quickBite.primary.pojo;

import com.quickBite.utils.TextUtils;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Setter
@Getter
@Document(value = "restaurant")
public class Restaurant extends _BasicEntity {

    private ObjectId vendorId;
    private Long seqId;
    private String orderPrefix; // Max 3-5 Char // ORD-1
    private String restaurantTitle;
    private String restaurantDescription;

    private String restaurantLicenseNumber;

    private String iconImageUrl;
    private boolean autoAcceptOrder = true;

    private boolean open = true;
    // Restaurant Address Details
    private RestaurantAddress restaurantAddress;

    private LocalTime restaurantClosingTime = LocalTime.of(00, 00, 00);
    private String restaurantClosedMessage;

    public static class RestaurantAddress {

        private Double latitude;
        private Double longitude;
        private String addressLine1;
        private String addressLine2;
        private String landMark;
        private String countryTitle;
        private String stateTitle;
        private String cityTitle;

        private String pinCode;

        @Override
        public String toString() {
            return (!TextUtils.isEmpty(addressLine1) ? addressLine1 + ", " : "") +
                    (!TextUtils.isEmpty(addressLine2) ? addressLine2 + ", " : "") +
                    (!TextUtils.isEmpty(landMark) ? landMark + ", " : "") +
                    (!TextUtils.isEmpty(cityTitle) ? cityTitle + ", " : "") +
                    (!TextUtils.isEmpty(stateTitle) ? stateTitle + ", " : "") +
                    (!TextUtils.isEmpty(countryTitle) ? countryTitle + ", " : "") +
                    (!TextUtils.isEmpty(pinCode) ? "(" + pinCode + ")" : "");
        }

    }

}