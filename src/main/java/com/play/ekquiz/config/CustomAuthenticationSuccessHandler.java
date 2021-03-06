package com.play.ekquiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${session.timeout}")
    private Integer sessionTimeout;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        System.out.println("onAuthenticationSuccess............");

        final HttpSession session = request.getSession();

        System.out.println("request.getHeader :: " +request.getHeader("Accept"));

            System.out.println("onAuthenticationSuccess............(2)");

            session.setMaxInactiveInterval(sessionTimeout);

            String redirectUrl = request.getContextPath() + "/main/quizBoard";

            response.getWriter().write("{\"success\":\"true\", \"msg\": \"ok\", \"redirectUrl\":\"" + redirectUrl + "\"}");
            response.flushBuffer();
            response.getWriter().close();
    }
}
