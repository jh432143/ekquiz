package com.play.ekquiz.controller;

import com.play.ekquiz.service.MainService;
import com.play.ekquiz.vo.MainVO;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MainService mainService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index () {
        return "redirect:/main/quizBoard";
    }

    @RequestMapping(value = "/main/quizBoard", method = RequestMethod.GET)
    public void quizBoard () {

    }

    @RequestMapping(value = "/main/checkAnswer", method = RequestMethod.POST)
    @ResponseBody
    public String checkAnswer (MainVO mainVO) {
        System.out.println("mainVO ::: "+mainVO.getAnswer());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return JSONObject.toJSONString(resultMap);
    }

    @RequestMapping(value = "/main/dbTest", method = RequestMethod.GET)
    @ResponseBody
    public String dbTest (MainVO mainVO) throws Exception {
        System.out.println("dbTest ::::::::: ");

        mainVO = mainService.getDbTest();
        System.out.println("mainVO.getAnswer() :::: "+mainVO.getAnswer());

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return JSONObject.toJSONString(resultMap);
    }

    @RequestMapping(value = "/main/deleteCache", method = RequestMethod.GET)
    @ResponseBody
    public String deleteCache (MainVO mainVO) throws Exception {
        System.out.println("deleteCache ::::::::: ");

       mainService.deleteCache();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return JSONObject.toJSONString(resultMap);
    }
}
