package com.yoga.usermanagement.domain.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private long mobileNum;
    private String city;
    private long zipCode;
}
