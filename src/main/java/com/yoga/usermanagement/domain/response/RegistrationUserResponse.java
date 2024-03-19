package com.yoga.usermanagement.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistrationUserResponse implements Serializable {

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("mobile_verified")
    private Boolean mobileVerified;

}
