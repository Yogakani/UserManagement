package com.yoga.launcher.service;

import org.springframework.stereotype.Component;

@Component
public interface MobileServices {

    boolean mobileLookUp(long mobileNum);
}
