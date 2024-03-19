package com.yoga.usermanagement.mapper;

import com.yoga.usermanagement.domain.dto.CustomerDto;
import com.yoga.usermanagement.domain.entity.Customer;
import com.yoga.usermanagement.domain.request.RegisterUserRequest;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static com.yoga.usermanagement.util.AESUtil.encrypt;

@UtilityClass
public class CustomerMapper {

    public static Customer convertCustomerFromRegistrationRequest(RegisterUserRequest registerUserRequest) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(registerUserRequest, customer);
        String encryptedPwd = encrypt(registerUserRequest.getPassword());
        customer.setPassword(encryptedPwd);
        customer.setCreateTs(LocalDateTime.now());

        return customer;
    }

    public static CustomerDto convertCustomerToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        BeanUtils.copyProperties(customer, dto);
        return dto;
    }
}
