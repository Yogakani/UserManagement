package com.yoga.launcher.service;

import com.yoga.launcher.domain.dto.CustomerDto;
import com.yoga.launcher.domain.dto.RegistrationDto;
import com.yoga.launcher.domain.request.AuthenticateUserRequest;
import com.yoga.launcher.domain.request.RegisterUserRequest;
import com.yoga.launcher.domain.request.ResetPasswordRequest;
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
