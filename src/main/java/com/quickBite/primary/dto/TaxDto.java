package com.quickBite.primary.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaxDto {

    @Getter
    @Setter
    public static class CreateTax {
        @NotNull private String title;
        @NotNull private String taxPercentage;

    }

    @Getter
    @Setter
    public static class UpdateTax {
        @NotNull private String title;
        @NotNull private String taxPercentage;
    }

    @Setter
    @Getter
    public static class GetList extends _BasePageRequest {
        private String search;
    }

    @Setter
    @Getter
    public static class DetailTax extends _BasicDto {
        private String vendorId;
        private String title;
        private String taxPercentage;
    }

}
