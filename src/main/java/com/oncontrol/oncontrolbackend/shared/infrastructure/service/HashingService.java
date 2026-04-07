package com.oncontrol.oncontrolbackend.shared.infrastructure.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String hash(String plainText) {
        return passwordEncoder.encode(plainText);
    }

    public boolean verify(String plainText, String hashedText) {
        return passwordEncoder.matches(plainText, hashedText);
    }
}
