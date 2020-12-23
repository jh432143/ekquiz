package com.play.ekquiz.vo;

import lombok.Data;

@Data
public class UserVO {
    private String email;
    private String password;
    private Integer user_key;
    private String user_auth;
    private String lock_yn;
    private Integer login_fail_count;
}
