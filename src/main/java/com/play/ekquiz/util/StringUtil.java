package com.play.ekquiz.util;

import java.util.Random;

public class StringUtil {
    /**
     * randomStr:앞에붙을 랜덤 대문자영문 갯수
     * randomNum:뒤에붙을 랜덤 숫자 갯수
     * exReturn: KH1234
     */
    public static String getRandomStr (int randomStr, int randomNum) throws Exception {
        Random random = new Random();
        String tempPassword = "";

        for (int i=0; i<randomStr; i++) {
            String rStr = String.valueOf((char) ((int) (random.nextInt(26)) + 65));
            tempPassword += rStr;
        }

        for (int i=0; i<randomNum; i++) {
            int rNum = random.nextInt(10);
            tempPassword += rNum;
        }

        return tempPassword;
    }
}
