package com.play.ekquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authentication)
            throws IOException, ServletException {

        System.out.println("onAuthenticationFailure............");

        for (Object principal : sessionRegistry.getAllPrincipals()) {
            List<SessionInformation> sessionList = sessionRegistry.getAllSessions(principal, false);
            if (sessionList.size() > 0) {
                for (int i = 0; i < sessionList.size(); i++) {
                    sessionList.get(i).expireNow();
                }
                response.getWriter().write("{\"success\":\"retry\"}");
                response.flushBuffer();
                response.getWriter().close();
            }
        }

        response.getWriter().write("{\"success\":\"false\", \"msg\": \"" + authentication.getLocalizedMessage() + "\"}");
        response.flushBuffer();
        response.getWriter().close();
    }
}