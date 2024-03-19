package com.yoga.usermanagement.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationDto {

    private boolean status;

    private String userName;

    private Boolean isEmailVerified;

    private Boolean isMobileVerified;
}
