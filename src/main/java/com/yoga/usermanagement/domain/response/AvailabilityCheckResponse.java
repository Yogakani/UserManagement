package com.yoga.usermanagement.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AvailabilityCheckResponse {

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("msg")
    private String msg;

    @JsonProperty("isAvailable")
    private Boolean isAvailable;

    @JsonProperty("otp")
    private Integer otp;

    @JsonProperty("email")
    private String email;
}
