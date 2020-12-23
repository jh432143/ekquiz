package com.play.ekquiz.mapper;

import com.play.ekquiz.vo.*;

import java.util.List;
import java.util.Map;

public interface TestMapper {
    MainVO getDbTest() throws Exception;

    int setAddUserByOnlyEmail(TestVO testVO) throws Exception;

    int setUserInfo(TestVO testVO) throws Exception;

    int setAddUserByEmailAndPhone(TestVO testVO)  throws Exception;

    List<TestQuizVO> getTestQuizList(String currentDate) throws Exception;

    List<TestHintVO> getTestHintList(Integer quiz_key) throws Exception;

    List<TestShintVO> getTestShintList(Integer quiz_key) throws Exception;

    TestQuizVO getAnswer(Integer quiz_key) throws Exception;

    UserVO getUser(Map<String, String> map);

    UserVO getUserByEmail(UserVO userVO) throws Exception;

    UserVO getUserByEmailAndPassword(UserVO userVO) throws Exception;

    void setLoginFailCount(UserVO userVO) throws Exception;

    void setLoginFailCountZero(UserVO userVO) throws Exception;

    void setLastAccessDate(UserVO userVO) throws Exception;
}
