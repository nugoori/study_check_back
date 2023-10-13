package com.it.todolist_back.config;

import com.it.todolist_back.exception.CustomAuthenticationEntryPoint;
import com.it.todolist_back.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // User객체는 IoC에 등록되면 안되기때문에 관련된 security에서 DI하고 insert할때 찾아서 사용
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
                .permitAll()
                .antMatchers("/admin/**")
                .hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 뒤에 filter 전에 앞의 filter 추가
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint);
    }
}
