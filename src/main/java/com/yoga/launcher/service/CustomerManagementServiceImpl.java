package com.yoga.launcher.service;

import com.yoga.launcher.domain.dto.CustomerDto;
import com.yoga.launcher.domain.dto.RegistrationDto;
import com.yoga.launcher.domain.entity.Customer;
import com.yoga.launcher.domain.entity.CustomerExt;
import com.yoga.launcher.domain.request.AuthenticateUserRequest;
import com.yoga.launcher.domain.request.RegisterUserRequest;
import com.yoga.launcher.domain.request.ResetPasswordRequest;
import com.yoga.launcher.mapper.CustomerMapper;
import com.yoga.launcher.repository.CustomerExtRepository;
import com.yoga.launcher.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.yoga.launcher.mapper.CustomerExtMapper.prepareCustomerExtForRegistration;
import static com.yoga.launcher.mapper.CustomerExtMapper.prepareRegistrationStatus;
import static com.yoga.launcher.mapper.CustomerMapper.convertCustomerFromRegistrationRequest;
import static com.yoga.launcher.util.AESUtil.decrypt;
import static com.yoga.launcher.util.AESUtil.encrypt;

@Service
@Slf4j
public class CustomerManagementServiceImpl implements CustomerManagementService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerExtRepository customerExtRepository;
    @Override
    public RegistrationDto registerUser(RegisterUserRequest registerUserRequest) {
        Customer customer = convertCustomerFromRegistrationRequest(registerUserRequest);
        customer = customerRepository.save(customer);

        if (customer.getId() > 0) {
            log.info("User creation successful for the userId : {} and the customerId is : {}",
                    customer.getUserName(), customer.getId());
            CustomerExt customerExt = prepareCustomerExtForRegistration(customer);
            customerExt = customerExtRepository.save(customerExt);

            if (customerExt.getId() > 0) {
                log.info("Customer Ext entry created and id is {}", customerExt.getId());
            }
            return prepareRegistrationStatus(customerExt, customer.getUserName(), true);
        }
        log.error("User creation failed while persisting in DB.");
        return prepareRegistrationStatus(null, null, false);
    }

    @Override
    public boolean checkForUserId(String userId) {
        Optional<Customer> optionalCustomer = customerRepository.findByUserName(userId);
        return optionalCustomer.isEmpty();
    }

    @Override
    public boolean checkForEmailAddress(String emailAddress) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmailId(emailAddress);
        return optionalCustomer.isEmpty();
    }

    @Override
    public boolean checkForMobileNumber(long mobileNum) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNum(mobileNum);
        return optionalCustomer.isEmpty();
    }

    @Override
    public int generateOTP() {
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[6];
        for (int i = 0; i < 6; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        int otpInt = Integer.parseInt(String.valueOf(otp));
        log.info("Generated OTP is : {}", otpInt);
        return otpInt;
    }

    @Override
    public boolean authenticate(AuthenticateUserRequest authenticateUserRequest) {
        Optional<Customer> customerOpt =  customerRepository.findByUserName(authenticateUserRequest.getUserName());
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            String dbPwd = decrypt(customer.getPassword());
            return authenticateUserRequest.getPassword().equals(dbPwd);
        }
        return false;
    }

    @Override
    public CustomerDto findCustomerAccount(String userName) {
        Optional<Customer> customerOpt = customerRepository.findByUserName(userName);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            log.info("User found with the User Name : {} and its id : {}", userName, customer.getId());
            return CustomerMapper.convertCustomerToDto(customer);
        }
        log.error("No match found for the User Name : {}", userName);
        return null;
    }

    @Override
    public boolean updatePassword(ResetPasswordRequest request) {
        Optional<Customer> customerOpt = customerRepository.findByUserName(request.getUserName());

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setPassword(encrypt(request.getPassword()));
            customer.setLastModTs(LocalDateTime.now());
            customerRepository.save(customer);
            log.info("Password Updated for the user : {}", request.getUserName());
            return true;
        }
        return false;
    }
}
