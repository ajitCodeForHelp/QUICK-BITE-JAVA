package com.quickBite.primary.dto;

import com.quickBite.primary.pojo.Restaurant;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantDto {

    @Getter
    @Setter
    public static class CreateRestaurant {
        @NotNull
        private String restaurantTitle;
    }

    @Getter
    @Setter
    public static class UpdateRestaurant {
        private String orderPrefix;
        @NotNull private String restaurantTitle;
        private String restaurantDescription;

        private String restaurantLicenseNumber;

        private String iconImageUrl;
        private boolean autoAcceptOrder = true;

        private Restaurant.RestaurantAddress restaurantAddress;

        private LocalTime restaurantClosingTime = LocalTime.of(00, 00, 00);
        private String restaurantClosedMessage;
    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }

    @Setter
    @Getter
    public static class DetailRestaurant extends _BasicDto {
        private String orderPrefix;
        private String restaurantTitle;
        private String restaurantDescription;

        private String restaurantLicenseNumber;

        private String iconImageUrl;
        private boolean autoAcceptOrder = true;

        private boolean open = true;
        private Restaurant.RestaurantAddress restaurantAddress;

        private LocalTime restaurantClosingTime = LocalTime.of(00, 00, 00);
        private String restaurantClosedMessage;
    }

}
