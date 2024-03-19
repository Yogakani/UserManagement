package com.yoga.usermanagement.service;

import com.yoga.usermanagement.domain.response.MobileNumLookupResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MobileServiceImpl implements MobileServices {
    @Autowired
    private VeriphoneService veriphoneService;

    @Override
    public boolean mobileLookUp(long mobileNum) {

        log.info("Mobile Number Lookup started..");
        MobileNumLookupResponse response = veriphoneService.verify(mobileNum);
        log.info("Response of verify : {}", response.toString());

        if (response.getStatus().equalsIgnoreCase("success")) {
            return response.getValidPhone();
        }
        return false;
    }


}
