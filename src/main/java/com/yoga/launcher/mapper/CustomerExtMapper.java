package com.yoga.launcher.mapper;

import com.yoga.launcher.domain.dto.RegistrationDto;
import com.yoga.launcher.domain.entity.Customer;
import com.yoga.launcher.domain.entity.CustomerExt;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.nonNull;

@UtilityClass
public class CustomerExtMapper {

    public static CustomerExt prepareCustomerExtForRegistration(Customer customer) {
        CustomerExt customerExt = new CustomerExt();
        customerExt.setCustomer(customer);
        customerExt.setEmailVerified(true);
        customerExt.setMobileVerified(false);
        customerExt.setCreateTs(LocalDateTime.now());

        return customerExt;
    }

    public static RegistrationDto prepareRegistrationStatus(CustomerExt customerExt, String userName, boolean status) {
        return new RegistrationDto()
                .setStatus(status)
                .setUserName(userName)
                .setIsEmailVerified(nonNull(customerExt) ? customerExt.isEmailVerified() : null)
                .setIsMobileVerified(nonNull(customerExt) ? customerExt.isMobileVerified() : null);
    }
}
