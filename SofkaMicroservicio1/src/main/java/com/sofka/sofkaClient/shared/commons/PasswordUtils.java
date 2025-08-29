package com.sofka.sofkaClient.shared.commons;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {


    public static String hash(String password) {
        return hash(password, 10);
    }

    public static String hash(String password, int strength) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(strength);
        return passwordEncoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return matches(rawPassword.toString(), encodedPassword, 10);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword, int strength) {
        return new BCryptPasswordEncoder(strength).matches(rawPassword, encodedPassword);
    }
}
