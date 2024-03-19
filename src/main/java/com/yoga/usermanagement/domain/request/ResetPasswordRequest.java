package com.yoga.usermanagement.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResetPasswordRequest implements Serializable {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;
}
