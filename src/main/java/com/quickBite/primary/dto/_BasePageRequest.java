package com.quickBite.primary.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class _BasePageRequest {

    private Integer pageNumber = 0;
    private Integer pageSize = 10;

}