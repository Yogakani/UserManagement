package com.yoga.usermanagement.service;

import com.yoga.usermanagement.domain.dto.CustomerDto;
import com.yoga.usermanagement.domain.dto.RegistrationDto;
import com.yoga.usermanagement.domain.request.AuthenticateUserRequest;
import com.yoga.usermanagement.domain.request.RegisterUserRequest;
import com.yoga.usermanagement.domain.request.ResetPasswordRequest;
import org.springframework.stereotype.Component;

@Component
public interface CustomerManagementService {

    RegistrationDto registerUser(RegisterUserRequest registerUserRequest);

    boolean checkForUserId(String userId);

    boolean checkForEmailAddress(String emailAddress);

    boolean checkForMobileNumber(long mobileNum);

    int generateOTP();

    boolean authenticate(AuthenticateUserRequest authenticateUserRequest);

    CustomerDto findCustomerAccount(String userName);

    boolean updatePassword(ResetPasswordRequest request);

}
