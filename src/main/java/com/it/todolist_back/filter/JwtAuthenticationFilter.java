package com.it.todolist_back.filter;

import com.it.todolist_back.entity.Authority;
import com.it.todolist_back.entity.Role;
import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.UserMapper;
import com.it.todolist_back.security.JwtTokenProvider;
import com.it.todolist_back.security.PrincipalUser;
import com.it.todolist_back.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthService authService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        System.out.println(authorization + "11"); // jwt token provider 빼면서 convert랑 안되는중?

        // provider 없이
        if(httpServletRequest.getRequestURI().startsWith("/auth") && !httpServletRequest.getRequestURI().startsWith("/authenticate")) {
            chain.doFilter(request, response); // 전처리 doFilter 후처리(-> 지금 UsernamePasswordAuthenticationFilter.class를 적용해 놈)
            return;
        }
        if(!StringUtils.hasText(authorization)){
            chain.doFilter(request, response); // 토큰이 필요한 요청에 토큰이 없는 경우( = Authentication 객체에 contextHolder에 ~~가 없는경우? ) 에 후처리로 넘어감
            return;
        }
        String accessToken = authorization.substring("Bearer ".length());
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode("1UQJ1AHIvUfFlCwj4WAauoat9iaJN4nP3bolvD0Zhnw1ZLWwuG8rH7I06jQgGgWorvdHuRNSo5DXij3MIoFKRw=="));

        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (Exception e) {
            chain.doFilter(request, response);  // token 만료 or 위조된 경우
            return;
        }

        String username = claims.get("username", String.class);
//        System.out.println(claims.get("auth", Collection.class) + " claims 객체"); // authorities -> front에서 사용

        // 트래픽 해결 ( DB까지 요청을 안보내기 ) 여기까지 인증 끝
        List<Object> authList = claims.get("auth", List.class);

        List<Authority> authorities = new ArrayList<>();
        
        authList.forEach(auth -> {
            Role role = new Role();
            role.setRoleName(((Map<String, String>) auth).get("authority"));
            Authority authority = new Authority();
            authority.setRole(role);
            authorities.add(authority);
        });

        User user = User.builder()
                .email(username)
                .authorities(authorities)
                .build();

        PrincipalUser principalUser = new PrincipalUser(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());

        // 트래픽이 적은 경우에 적합한 방법
//        User user = userMapper.findUserByEmail(username);
//        PrincipalUser principalUser = new PrincipalUser(user);
//        Authentication authentication =
//                new UsernamePasswordAuthenticationToken(principalUser, null, principalUser.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);


        chain.doFilter(request, response);

//        String jwtToken = jwtTokenProvider.convertToken(authorization);
//        String uri = httpServletRequest.getRequestURI();
//
//        if(!uri.startsWith("/auth") && jwtTokenProvider.validateToken(jwtToken)) {
//            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        chain.doFilter(request, response);
    }
}
