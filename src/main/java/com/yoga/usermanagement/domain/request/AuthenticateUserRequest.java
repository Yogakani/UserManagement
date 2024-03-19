package com.yoga.usermanagement.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
public class AuthenticateUserRequest implements Serializable {

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("password")
    private String password;
}
