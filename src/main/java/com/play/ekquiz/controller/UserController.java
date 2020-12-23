package com.play.ekquiz.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

@Controller
public class UserController {
    // 회원가입 페이지

    // 회원가입 처리

    // 회원정보 수정정

    // 회원탈퇴 처리

    // 패스워드 찾기 메일보내기
    @RequestMapping(value = "/user/findPassword", method = RequestMethod.GET)
    @ResponseBody
    public String findPassword () throws Exception {
        System.out.println("UserController - findPassword");
        Map<String, String> resultMap = new HashMap<>();

        String bodyEncoding = "UTF-8";
        String subject = "Quiz스푼 임시 패스워드";
        String fromEmail = "quizspoon@gmail.com";
        String fromUsername = "quizspoon admin";
        String toEmail = "jh432143@naver.com";

        String username = "quizspoon@gmail.com";
        String password = "dnqyhwswiflosbqt";

        Random random = new Random();
        String tempPassword = "";

        String randomStr = String.valueOf((char) ((int) (random.nextInt(26)) + 65));
        tempPassword = tempPassword + randomStr;
        for (int i=0; i<5; i++) {
            int randomNum = random.nextInt(10);
            tempPassword += randomNum;
        }

        StringBuffer sb = new StringBuffer();
        sb.append("<h2>안녕하세요. Quiz'스푼입니다.</h2>\n");
        sb.append("<h4>임시 패스워드를 발급해드립니다.</h4>\n");
        sb.append("<h4>지금 로그인 후 패스워드를 변경해주세요.</h4>\n");
        sb.append("<h2>패스워드 : "+ tempPassword +"</h2>\n\n");
        sb.append("https://www.naver.com\n");
        String html = sb.toString();

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.quitwait", "false");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Session session = Session.getInstance(properties, authenticator);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail, fromUsername));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
        message.setSubject(subject);
        message.setSentDate(new Date());

        Multipart mParts = new MimeMultipart();
        MimeBodyPart mTextPart = new MimeBodyPart();

        mTextPart.setText(html, bodyEncoding, "html");
        mParts.addBodyPart(mTextPart);

        message.setContent(mParts);

        MailcapCommandMap MailcapCmdMap = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        MailcapCmdMap.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        MailcapCmdMap.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        MailcapCmdMap.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        MailcapCmdMap.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        MailcapCmdMap.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(MailcapCmdMap);

        Transport.send( message );

        // 임시 패스워드 전송 후 user table 패스워드도 변경.

        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return JSONObject.toJSONString(resultMap);
    }
}
