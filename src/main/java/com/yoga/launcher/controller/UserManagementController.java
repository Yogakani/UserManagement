package com.yoga.launcher.controller;

import com.yoga.launcher.annotation.RestCall;
import com.yoga.launcher.domain.dto.CustomerDto;
import com.yoga.launcher.domain.dto.RegistrationDto;
import com.yoga.launcher.domain.request.RegisterUserRequest;
import com.yoga.launcher.domain.request.ResetPasswordRequest;
import com.yoga.launcher.domain.response.AvailabilityCheckResponse;
import com.yoga.launcher.domain.response.RegistrationUserResponse;
import com.yoga.launcher.domain.response.ResetPasswordResponse;
import com.yoga.launcher.service.CustomerManagementService;
import com.yoga.launcher.service.MailPublisher;
import com.yoga.launcher.service.MobileServices;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
@Slf4j
public class UserManagementController {

    @Autowired
    private CustomerManagementService customerManagementService;

    @Autowired
    private MailPublisher mailPublisher;

    @Autowired
    private MobileServices mobileServices;

    @PostMapping("/register")
    @RestCall
    public ResponseEntity<RegistrationUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) {
        log.info("Registering the user");

        if (nonNull(registerUserRequest) && StringUtils.hasText(registerUserRequest.getUserName())) {
            RegistrationDto registration = customerManagementService.registerUser(registerUserRequest);

            if (registration.isStatus()) {
                log.info("User successfully created!");
                return new ResponseEntity<>(prepareRegistrationUserResponse(HttpStatus.OK.value(),
                        "User successfully created", registration), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(prepareRegistrationUserResponse(HttpStatus.OK.value(),
                        "User creation failed", registration), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            log.error("Invalid data.");
            return new ResponseEntity<>(prepareRegistrationUserResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Invalid data", null), HttpStatus.OK);
        }
    }

    private RegistrationUserResponse prepareRegistrationUserResponse(Integer code, String msg, RegistrationDto dto) {
        return new RegistrationUserResponse()
                .setMsg(msg)
                .setCode(code)
                .setUserName(nonNull(dto) ? dto.getUserName() : null)
                .setEmailVerified(nonNull(dto) ? dto.getIsEmailVerified() : null)
                .setMobileVerified(nonNull(dto) ? dto.getIsMobileVerified() : null);
    }

    @GetMapping("/userIdAvailabilityCheck")
    @RestCall
    public ResponseEntity<AvailabilityCheckResponse> checkForUserIdAvailability(@RequestHeader("user_id") String userId) {
        log.info("Started checking of userId Availability.");
        boolean available = customerManagementService.checkForUserId(userId);

        if (available) {
            log.info("User Id is new.");
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                    "User ID is available", true, null, null), HttpStatus.OK );
        } else {
            log.info("User Id already present in the system.");
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                    "User ID is not available", false, null, null), HttpStatus.OK );
        }
    }

    private AvailabilityCheckResponse prepareAvailabilityResponse(Integer code, String msg, boolean available,
                                                                  Integer otp, String email) {
        return new AvailabilityCheckResponse()
                        .setCode(code)
                        .setMsg(msg)
                        .setIsAvailable(available)
                        .setOtp(otp)
                        .setEmail(email);
    }

    @GetMapping(value = {"/emailIdAvailabilityCheck", "/resendEmailOTP"})
    @RestCall
    public ResponseEntity<AvailabilityCheckResponse> checkForEmailIdAvailability(@RequestHeader("email_id") String emailId) throws MessagingException, UnsupportedEncodingException {
        log.info("Started checking of EmailId Availability.");
        boolean available = customerManagementService.checkForEmailAddress(emailId);

        if (available) {
            log.info("Email Id is new.");
            int otp = customerManagementService.generateOTP();
            mailPublisher.sendOTP(emailId, otp);
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                    "Email ID is new", true, otp, emailId), HttpStatus.OK );
        } else {
            log.info("Email Id already present in the system.");
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                    "Email ID is already associated with another account in the system", false,
                    null, emailId), HttpStatus.OK );
        }
    }

    @GetMapping("/mobileNumAvailabilityCheck")
    @RestCall
    public ResponseEntity<AvailabilityCheckResponse> checkForMobileNumAvailability(@RequestHeader("mobile_num") long mobileNum) {
        log.info("Started checking of Mobile Num Availability.");
        boolean available = customerManagementService.checkForMobileNumber(mobileNum);

        if (available) {
            log.info("Mobile Num is new.");
            boolean valid = mobileServices.mobileLookUp(mobileNum);

            if (valid) {
                log.info("Mobile Num is valid.");
                return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                        "Mobile Num is new", true, null, null), HttpStatus.OK );
            } else {
                log.info("Invalid Mobile Num.");
                return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                        "Mobile Num is invalid", false, null, null), HttpStatus.OK);
            }
        } else {
            log.info("Mobile Num already present in the system.");
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                    "Mobile Num is already associated with another account in the system",
                    false, null, null), HttpStatus.OK );
        }
    }

    @GetMapping("/findAccount")
    @RestCall
    public ResponseEntity<AvailabilityCheckResponse> findAccount(@RequestHeader("user_name") String userName) throws MessagingException, UnsupportedEncodingException {
        log.info("Started Find Account..");

        CustomerDto dto = customerManagementService.findCustomerAccount(userName);
        if (dto == null) {
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(),
                    "No matches found with the User Name", false, null, null),
                    HttpStatus.OK);
        } else {
            int otp = customerManagementService.generateOTP();
            mailPublisher.sendOTP(dto.getEmailId(), otp);
            return new ResponseEntity<>(prepareAvailabilityResponse(HttpStatus.OK.value(), "User found",
                    true, otp, dto.getEmailId()), HttpStatus.OK);
        }
    }

    @PostMapping("/resetPassword")
    @RestCall
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        log.info("Started Reset Password...");
        boolean status = customerManagementService.updatePassword(request);

        if (status) {
            return new ResponseEntity<>(prepareResetPasswordResponse("Password Updated",
                    HttpStatus.OK.value(), true), HttpStatus.OK);
        } else {
            log.error("Password Update failed, user_name : {}", request.getUserName());
            return new ResponseEntity<>(prepareResetPasswordResponse("Password Update failed",
                    HttpStatus.OK.value(), false), HttpStatus.OK);
        }
    }

    private ResetPasswordResponse prepareResetPasswordResponse(String msg, Integer code, boolean updated) {
        return new ResetPasswordResponse()
                        .setCode(code)
                        .setMsg(msg)
                        .setUpdated(updated);
    }


}
