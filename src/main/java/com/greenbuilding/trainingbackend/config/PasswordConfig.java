package com.greenbuilding.trainingbackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Exposes the password encoder used to hash stored passwords.
@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt is the standard encoder used by Spring Security for password storage.
        return new BCryptPasswordEncoder();
    }
}
