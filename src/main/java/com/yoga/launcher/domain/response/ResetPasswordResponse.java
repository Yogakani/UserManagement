package com.yoga.launcher.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
@Data
@Accessors(chain = true)
public class ResetPasswordResponse implements Serializable {
    @JsonProperty("msg")
    private String msg;

    @JsonProperty("code")
    private Integer code;

    @JsonProperty("updated")
    private Boolean updated;
}
