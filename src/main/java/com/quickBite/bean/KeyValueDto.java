package com.quickBite.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KeyValueDto {
    private String key;
    private String value;
    private String label;

    public KeyValueDto(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
