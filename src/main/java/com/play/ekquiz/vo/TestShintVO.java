package com.play.ekquiz.vo;

import lombok.Data;

@Data
public class TestShintVO {
    private Integer shint_key;
    private Integer quiz_key;
    private Integer shint_category_key;
    private String shint;
    private String submit_date;
}
