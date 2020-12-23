package com.play.ekquiz.vo;

import lombok.Data;

import java.util.List;

@Data
public class TestQuizVO {
    private Integer quiz_key;
    private String submit_date;
    private String answer;
    private String answer_check_yn;
    private Integer cash_prize = 0;
    private List<TestHintVO> testHintList = null;
    private List<TestShintVO> testShintList = null;
}
