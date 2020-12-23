package com.play.ekquiz.vo;

import lombok.Data;

@Data
public class TestHintVO {
    private Integer hint_key;
    private Integer quiz_key;
    private Integer hint_sequence;
    private String submit_date;
    private String hint_time;
    private String hint;
}
