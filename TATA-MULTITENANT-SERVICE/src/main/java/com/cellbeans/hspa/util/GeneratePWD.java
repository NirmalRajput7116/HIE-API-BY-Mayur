package com.cellbeans.hspa.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class GeneratePWD {

    public String generateRandomPWD() {
//      String randomPWD =  UUID.randomUUID().toString();
        String randomPWD = RandomStringUtils.randomAlphanumeric(8);
        return randomPWD;
    }
}
