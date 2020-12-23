package com.play.ekquiz.config;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CustomPasswordEncoder implements PasswordEncoder {

    private MessageDigest messageDigest;

    public CustomPasswordEncoder() throws Exception {
        this.messageDigest = MessageDigest.getInstance("SHA-256");
    }

    public CustomPasswordEncoder(String type) {
        try {
            switch (type) {
                case "SHA-128":
                    this.messageDigest = MessageDigest.getInstance("SHA-128");
                    break;
                case "SHA-256":
                    this.messageDigest = MessageDigest.getInstance("SHA-256");
                    break;
                case "SHA-512":
                    this.messageDigest = MessageDigest.getInstance("SHA-512");
                    break;
                default:
                    break;
            }
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public String encode(CharSequence rawPassword) {
        byte[] hash = messageDigest.digest((rawPassword.toString()).getBytes(StandardCharsets.UTF_8));
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10)
                buf.append("0");

            buf.append(Long.toString(hash[i] & 0xff, 16));
        }

        return buf.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

    public static void main(String[] args) {
        CustomPasswordEncoder passwordEncoder = null;
        try {
            passwordEncoder = CustomPasswordEncoderFactory.getEncoderInstanceSha256();
            System.out.println(passwordEncoder.encode(args[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
