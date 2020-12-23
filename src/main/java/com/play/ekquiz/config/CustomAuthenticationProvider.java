package com.play.ekquiz.config;

import com.play.ekquiz.service.TestService;
import com.play.ekquiz.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private TestService testService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authenticate..............");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        CustomPasswordEncoder passwordEncoder = null;
        try {
            passwordEncoder = CustomPasswordEncoderFactory.getEncoderInstanceSha256();
        } catch (Exception e) {
        }

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("email :: "+email);
        System.out.println("password :: "+password);

        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", passwordEncoder.encode(password));

        UserVO userVO = testService.getUser(map);

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if ("0".equals(userVO.getUser_auth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("1".equals(userVO.getUser_auth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
        } else if ("2".equals(userVO.getUser_auth())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }

        return new UsernamePasswordAuthenticationToken(userVO, passwordEncoder.encode(password), authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}