package com.quickBite.primary.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CouponCodeDto {

    @Getter
    @Setter
    public static class CreateCouponCode {
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double discountValue;
    }

    @Getter
    @Setter
    public static class UpdateCouponCode {
        private String title;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double discountValue;

    }
    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }
    @Setter
    @Getter
    public static class DetailCouponCode extends _BasicDto{
        private String title;
        private String couponCode;
        private LocalDate startDate;
        private LocalDate endDate;
        private Double discountValue;
    }

}
