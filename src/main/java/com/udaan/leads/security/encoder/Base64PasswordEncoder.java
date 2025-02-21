package com.udaan.leads.security.encoder;

import java.util.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Base64PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        // Encoding password using Base64
        return Base64.getEncoder().encodeToString(rawPassword.toString().getBytes());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Decoding the stored Base64 password and comparing
        return encodedPassword.equals(encode(rawPassword));
    }
}

