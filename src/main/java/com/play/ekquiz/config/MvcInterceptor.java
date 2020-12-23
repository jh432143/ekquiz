package com.play.ekquiz.config;

import com.play.ekquiz.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class MvcInterceptor extends HandlerInterceptorAdapter {
    final String checkUrl = "/main/checkAnswer,";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String actionUri = request.getRequestURI().toString();
        System.out.println("actionUri :: "+actionUri);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("userEmail :: "+authentication.getName()); //anonymousUser

        if (checkUrl.contains(actionUri)) {
            if ("anonymousUser".equals(authentication.getName())) {
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write("{\"status\":\"false\", \"code\":\"1002\", \"msg\": \"로그인이 필요한 서비스 입니다. 로그인 페이지로 이동합니다.\"}");
                response.flushBuffer();
                response.getWriter().close();
                return false;
            }
        }





        /*
        되는거..
        UserVO userVO = ((UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (userVO != null) {
            System.out.println("--->>> "+userVO.getEmail());
        }*/

        setSystemTopMenuAndSubMenuSelected(request, response);

        return true;
    }

    // 탑메뉴 서브메뉴 쿠키셋팅
    private void setSystemTopMenuAndSubMenuSelected(HttpServletRequest request, HttpServletResponse response) {
        final String actionUrl = request.getRequestURL().toString();
        System.out.println("actionUri :: "+actionUrl);
    }
}