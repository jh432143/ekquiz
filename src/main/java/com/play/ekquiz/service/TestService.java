package com.play.ekquiz.service;

import com.play.ekquiz.config.CustomPasswordEncoder;
import com.play.ekquiz.config.CustomPasswordEncoderFactory;
import com.play.ekquiz.mapper.TestMapper;
import com.play.ekquiz.util.StringUtil;
import com.play.ekquiz.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;

@Service
public class TestService {
    @Autowired
    private TestMapper testMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> setAddUserByOnlyEmail(TestVO testVO) throws Exception {
        System.out.println("setAddUserByOnlyEmail....");
        String tempPassword = StringUtil.getRandomStr(1, 5);
        CustomPasswordEncoder passwordEncoder = CustomPasswordEncoderFactory.getEncoderInstanceSha256();
        String encodePassword = passwordEncoder.encode(tempPassword);
        testVO.setPassword(encodePassword);

        System.out.println("tempPassword : "+tempPassword);
        System.out.println("encodePassword : "+encodePassword);

        int resultCount = 0;

        // user insert
        resultCount = testMapper.setAddUserByOnlyEmail(testVO);

        // user key base user_info insert
        if (0 < resultCount) {
            resultCount = testMapper.setUserInfo(testVO);
        }

        // user password send email
        String toEmail = testVO.getEmail();
        String toPassword = tempPassword;

        if (0 < resultCount) {
            String bodyEncoding = "UTF-8";
            String subject = "Quiz스푼 임시 패스워드";
            String fromEmail = "quizspoon@gmail.com";
            String fromUsername = "quizspoon admin";

            String username = "quizspoon@gmail.com";
            String password = "dnqyhwswiflosbqt";

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
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "true");
        resultMap.put("msg", "성공");
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> setAddUserByEmailAndPhone(TestVO testVO) throws Exception {
        CustomPasswordEncoder passwordEncoder = CustomPasswordEncoderFactory.getEncoderInstanceSha256();
        String encodePassword = passwordEncoder.encode(testVO.getPassword());
        testVO.setPassword(encodePassword);

        int resultCount = 0;
        // user insert
        resultCount = testMapper.setAddUserByEmailAndPhone(testVO);

        // user key base user_info insert
        if (0 < resultCount) {
            resultCount = testMapper.setUserInfo(testVO);
        }

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("status", "true");
        resultMap.put("msg", "성공");

        return resultMap;
    }

    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "getQuizList") // 10분간 호출없을때 캐시제거 / 30분에 한번씩 캐시 제거.
    public List<TestQuizVO> getTestQuizList(String currentDate) throws Exception {
        return testMapper.getTestQuizList(currentDate);
    }

    public List<TestHintVO> getTestHintList(Integer quiz_key) throws Exception {
        return testMapper.getTestHintList(quiz_key);
    }

    @Cacheable(cacheNames = "getShintList") // 30분간 호출없을때 캐시제거 / 30분에 한번씩 캐시 제거.
    public List<TestShintVO> getTestShintList(Integer quiz_key) throws Exception {
        return testMapper.getTestShintList(quiz_key);
    }

    @Cacheable(cacheNames = "getAnswer")
    public TestQuizVO getAnswer(Integer quiz_key) throws Exception {
        return testMapper.getAnswer(quiz_key);
    }

    @CacheEvict(cacheNames = "getQuizList", allEntries = true)
    public void evictGetTestQuizList() {
    }

    @CacheEvict(cacheNames = "getAnswer", allEntries = true)
    public void evictGetAnswer() {
    }

    public UserVO getUser(Map<String, String> map) {
        return testMapper.getUser(map);
    }

    @Transactional(readOnly = true)
    public UserVO getUserByEmail(UserVO userVO) throws Exception {
        return testMapper.getUserByEmail(userVO);
    }

    @Transactional(readOnly = true)
    public UserVO getUserByEmailAndPassword(UserVO userVO) throws Exception {
        return testMapper.getUserByEmailAndPassword(userVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setLoginFailCount(UserVO userVO) throws Exception {
        testMapper.setLoginFailCount(userVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setLoginFailCountZero(UserVO userVO) throws Exception {
        testMapper.setLoginFailCountZero(userVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void setLastAccessDate(UserVO userVO) throws Exception {
        testMapper.setLastAccessDate(userVO);
    }
}
