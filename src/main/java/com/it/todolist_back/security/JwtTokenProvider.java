package com.it.todolist_back.security;

import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.UserMapper;
import com.it.todolist_back.service.PrincipalUserService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

@Service
public class JwtTokenProvider {

    private final Key key;
    private final PrincipalUserService principalUserService;
    private final UserMapper userMapper;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Autowired PrincipalUserService principalUserService,
                            @Autowired UserMapper userMapper) {
        key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.principalUserService = principalUserService;
        this.userMapper = userMapper;
    }

    public String generateAccessToken(Authentication authentication) {
        String accessToken = null;
        //
        PrincipalUser principalUser = (PrincipalUser) authentication.getPrincipal();

        Date tokenExpiresDate = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));

        JwtBuilder jwtBuilder = Jwts.builder()
                .claim("auth", authentication.getAuthorities())
                .setSubject("AccessToken")
                .setExpiration(tokenExpiresDate)
                .signWith(key, SignatureAlgorithm.HS256);

        User user = userMapper.findUserByEmail(principalUser.getUsername());
        System.out.println(user + "??");
        if(user == null) {
            return "유저 정보가 존재하지 않습니다.";
        }
        return jwtBuilder.claim("username", principalUser.getUsername()).compact();
        // claim("username", authenrication객체에서 데이터(보통 로그인 아이디나 권한 정보를 넣음)를 가져와서 넣을 수도 있음)
    }

    // filter
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // filter
    public Authentication getAuthentication(String accessToken) {
        Authentication authentication = null;
        String username = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("username")
                .toString();

        PrincipalUser principalUser = (PrincipalUser) principalUserService.loadUserByUsername(username);

        authentication = new UsernamePasswordAuthenticationToken(principalUser, null);
        return authentication;
    }

    public String convertToken(String token) {
        // System.out.println(token + "베어러로 변환"); 여기선 token null??
        String type = "Bearer ";

        if(StringUtils.hasText(token) && token.startsWith(type)) {
            return token.substring(type.length());
        }
        return "";
    }

}
