package com.cellbeans.hspa.util;

import org.springframework.stereotype.Service;

@Service
public class GenerateOTP {

    public int generateRandomOTP() {
        // Random random = new Random();
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        return randomPIN;
    }
}
