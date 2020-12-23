package com.play.ekquiz.config;

public class CustomPasswordEncoderFactory {
    private static CustomPasswordEncoder passwordEncoder;

    public static CustomPasswordEncoder getEncoderInstanceSha128() throws Exception {
        passwordEncoder = new CustomPasswordEncoder("SHA-128");
        return passwordEncoder;
    }

    public static CustomPasswordEncoder getEncoderInstanceSha256() throws Exception {
        passwordEncoder = new CustomPasswordEncoder("SHA-256");
        return passwordEncoder;
    }

    public static CustomPasswordEncoder getEncoderInstanceSha512() throws Exception {
        passwordEncoder = new CustomPasswordEncoder("SHA-512");
        return passwordEncoder;
    }
}