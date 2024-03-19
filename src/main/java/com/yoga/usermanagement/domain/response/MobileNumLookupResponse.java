package com.yoga.usermanagement.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileNumLookupResponse implements Serializable {

    @JsonProperty("status")
    private String status;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("phone_valid")
    private Boolean validPhone;

    @JsonProperty("phone_type")
    private String phoneType;

    @JsonProperty("phone_region")
    private String phoneRegion;

    @JsonProperty("country")
    private String country;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("country_prefix")
    private String countryPrefix;

    @JsonProperty("international_number")
    private String internationalNumber;

    @JsonProperty("local_number")
    private String localNumber;

    @JsonProperty("e164")
    private String e164;

    @JsonProperty("carrier")
    private String carrier;
}
