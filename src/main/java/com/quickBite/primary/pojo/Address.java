package com.quickBite.primary.pojo;


import com.quickBite.utils.TextUtils;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(value = "address")
public class Address extends _BasicEntity {

    private ObjectId customerId;
    private String customerName;
    private String customerContact;

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
