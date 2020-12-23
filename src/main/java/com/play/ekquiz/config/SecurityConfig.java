package com.play.ekquiz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomAuthenticationOutSuccessHandler customAuthenticationOutSuccessHandler;

    @Override
    public void configure (WebSecurity web) throws Exception {
        System.out.println("WebSecurity.....");
        web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error/**");
    }

    @Override
    public void configure (HttpSecurity http) throws Exception {
        System.out.println("HttpSecurity.....");
        http.sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login")
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(this.sessionRegistry());

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**", "error/**").permitAll()
                .antMatchers("/login", "/logout", "/loginProcess", "/main/quizBoard", "/main/checkAnswer", "/user/findPassword", "/main/dbTest").permitAll()
                .antMatchers("/main/deleteCache", "/test/**").permitAll()
                .and()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginProcessingUrl("/loginProcess")
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .defaultSuccessUrl("/loginProcess")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(customAuthenticationOutSuccessHandler)
                .logoutSuccessUrl("/login")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/access-denied")
                .and()
            .headers()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable()
                .and()
            .headers()
                .contentTypeOptions()
                .and()
            .cacheControl().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.customAuthenticationProvider);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
}
