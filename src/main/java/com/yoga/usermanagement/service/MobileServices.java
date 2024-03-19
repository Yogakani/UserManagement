package com.yoga.usermanagement.service;

import org.springframework.stereotype.Component;

@Component
public interface MobileServices {

    boolean mobileLookUp(long mobileNum);
}
