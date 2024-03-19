package com.yoga.launcher.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserRequest implements Serializable {
    @JsonProperty("email_id")
    private String emailId;

    @JsonProperty("password")
    private String password;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("mobile_num")
    private Long mobileNum;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zip_code")
    private Long zipCode;
}
