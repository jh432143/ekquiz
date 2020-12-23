package com.play.ekquiz.controller;

import com.play.ekquiz.config.CustomPasswordEncoder;
import com.play.ekquiz.config.CustomPasswordEncoderFactory;
import com.play.ekquiz.service.TestService;
import com.play.ekquiz.util.Compare;
import com.play.ekquiz.util.DateUtil;
import com.play.ekquiz.vo.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    /*
        회원가입페이지
     */
    @RequestMapping(value = "/test/addUserPage", method = RequestMethod.GET)
    public void addUserPage () {
        Map<String, String> map = new HashMap<>();
    }

    /*
        회원가입
     */
    @RequestMapping(value = "/test/addUser", method = RequestMethod.POST)
    @ResponseBody
    public String addUser (TestVO testVO) throws Exception {
        System.out.println("testvo :: "+testVO.getEmail());
        Map<String, String> resultMap = new HashMap<>();

        if ("Y".equals(testVO.getEmail_only_yn())) {
            resultMap = testService.setAddUserByOnlyEmail(testVO);
        } else {
            resultMap = testService.setAddUserByEmailAndPhone(testVO);
        }

        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return JSONObject.toJSONString(resultMap);
    }

    /*
        메인 보드
     */
    @RequestMapping(value = "/test/board", method = RequestMethod.GET)
    public void board (Model model) throws Exception {
        String searchDate = "";

        boolean isTime = DateUtil.isBeforeTime(07, 00, 00); // 매일 새 게임 시작 시간 07시.
        if (isTime) {
            searchDate = DateUtil.getMinusDay(1);
        } else {
            searchDate = DateUtil.getToDay();
        }

        System.out.println("searchDate : "+searchDate);

        List<TestQuizVO> testQuizList = testService.getTestQuizList(searchDate);

        for (TestQuizVO data : testQuizList) {
            data.setTestHintList(testService.getTestHintList(data.getQuiz_key()));
            data.setTestShintList(testService.getTestShintList(data.getQuiz_key()));
        }

        model.addAttribute("testQuizList", testQuizList);
    }

    /*
        정답확인
     */
    @RequestMapping(value = "/test/answerCheck", method = RequestMethod.GET)
    @ResponseBody
    public String answerCheck(TestQuizVO testQuizVO) throws Exception {
        System.out.println("testQuizVO :: "+testQuizVO.getAnswer());

        String reqAnswer = testQuizVO.getAnswer();
        int reqQuizKey = testQuizVO.getQuiz_key();

        testQuizVO = testService.getAnswer(reqQuizKey);

        if ("Y".equals(testQuizVO.getAnswer_check_yn())) {
            System.out.println("이미 누군가 맞춘 문제");

        } else {
            if (reqAnswer.equals(testQuizVO.getAnswer())) {
                System.out.println("정답입니다.");
                // 캐시삭제
                testService.evictGetTestQuizList();
                testService.evictGetAnswer();
            } else {
                System.out.println("정답이 아닙니다.");
            }
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return JSONObject.toJSONString(resultMap);
    }

    /*
        로그인
     */
    @RequestMapping(value = "/test/loginCheck", method = RequestMethod.POST)
    @ResponseBody
    public String loginCheck(UserVO userVO) throws Exception {
        System.out.println("userVO :: "+userVO.getEmail());
        Map<String, String> resultMap = new HashMap<>();

        UserVO dbUser = testService.getUserByEmail(userVO);

        if (Compare.isNotEmpty(dbUser)) {
            if (5 < dbUser.getLogin_fail_count()) {
                resultMap.put("status", "false");
                resultMap.put("msg", "로그인 실패 5회이상..계정.. 비밀번호찾기요망.");

                return JSONObject.toJSONString(resultMap);
            }

            CustomPasswordEncoder passwordEncoder = CustomPasswordEncoderFactory.getEncoderInstanceSha256();
            userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
            dbUser = testService.getUserByEmailAndPassword(userVO);

            if (Compare.isEmpty(dbUser)) {
                resultMap.put("status", "false");
                resultMap.put("msg", "비밀번호를 잘못입력.");
                testService.setLoginFailCount(userVO);

            } else {
                resultMap.put("status", "true");
                resultMap.put("msg", "로그인성공.");
                testService.setLoginFailCountZero(userVO);
                testService.setLastAccessDate(userVO);

            }

        } else {
            resultMap.put("status", "false");
            resultMap.put("msg", "계정이 없다.");
        }

        return JSONObject.toJSONString(resultMap);
    }
}
