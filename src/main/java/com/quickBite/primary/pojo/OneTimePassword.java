package com.quickBite.primary.pojo;

import com.quickBite.primary.pojo.enums.MobileEmailEnum;
import com.quickBite.primary.pojo.enums.VerificationTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class OneTimePassword extends _BasicEntity {

    private MobileEmailEnum sentOnType;

    private String otpSentOn;

    private String otpCode;

    private VerificationTypeEnum verificationType;

    private LocalDateTime expiredAt;

    private boolean expired = false;
}
