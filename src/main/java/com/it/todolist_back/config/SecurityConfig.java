package com.it.todolist_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(); // -> WebMvcConfigurer에서 설정한 값 IoC에서 찾음
        http.csrf().disable();

//        http.authorizeHttpRequests(); (AuthorizeHttpRequestsConfigurer)으로 반환

        // (ExpressionUrlAuthorizationConfigurer) 반환
        http.authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll();
//                .anyRequest()
//                .authenticated();
    }
}
