package com.yoga.launcher.controller;

import com.yoga.launcher.annotation.RestCall;
import com.yoga.launcher.domain.request.AuthenticateUserRequest;
import com.yoga.launcher.domain.response.AuthenticateUserResponse;
import com.yoga.launcher.service.CustomerManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    private CustomerManagementService customerManagementService;

    @PostMapping("/authenticate")
    @RestCall
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(@RequestBody AuthenticateUserRequest authenticateUserRequest) {
        log.info("Authenticating the user");

        if(nonNull(authenticateUserRequest)) {
            boolean authenticate = customerManagementService.authenticate(authenticateUserRequest);
            if (authenticate) {
                log.info("User successfully authenticated!");
                return new ResponseEntity<>(prepareAuthenticateUserResponse(HttpStatus.OK.value(),
                        "User successfully authenticated", authenticateUserRequest.getUserName()), HttpStatus.OK);
            } else {
                log.error("User authentication failed");
                return new ResponseEntity<>(prepareAuthenticateUserResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Invalid User", authenticateUserRequest.getUserName()), HttpStatus.OK);
            }
        } else {
            log.error("Invalid User.");
            return new ResponseEntity<>(prepareAuthenticateUserResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Invalid User", null), HttpStatus.OK);
        }
    }

    private AuthenticateUserResponse prepareAuthenticateUserResponse(Integer code, String msg, String userName) {
        return new AuthenticateUserResponse()
                    .setCode(code)
                    .setMsg(msg)
                    .setUserName(userName);
    }
}
