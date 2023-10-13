package com.it.todolist_back.service;

import com.it.todolist_back.dto.SignInReqDto;
import com.it.todolist_back.dto.SignUpReqDto;
import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.UserMapper;
import com.it.todolist_back.security.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final JwtTokenProvider jwtTokenProvider;

//    public Boolean isDuplicated(String email) {
//        Boolean result = false;
//
//        // null인지 확인 -> 재사용성이 높음
//        result = userMapper.findUserByEmail(email) != null;
//
////        // count횟수
////        result = userMapper.getUserCountByEmail(email) > 0;
//
//        return result;
//    }
//
//    @Transactional(rollbackFor = Exception.class)
//    public Boolean insertUser(SignUpReqDto signUpReqDto) {
//        Boolean result = false;
//        User user = signUpReqDto.toUser(passwordEncoder);
//        result = userMapper.saveUser(user) > 0;
//
//        return result;
//    }
//
//    public String signIn(SignInReqDto signInReqDto) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(signInReqDto.getEmail(), signInReqDto.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        System.out.println(authentication + " autentication");
////        System.out.println(authentication.getName()); -> 아무런 반응이없음 = 예외처리가 안되어있거나 예외가 print가 안됨 -> security에서 발생하는 모든예외를 처리하기위해 config에 예외처리를위한 코드 추가
//
//        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
//        //
//        System.out.println(accessToken + "생성되야되는거 아닌가");
//        return accessToken;
//    }

//    public Boolean authenticate(String token) {
//        System.out.println(token + "convert하기 전"); // 안찍힘
//        String accessToken = jwtTokenProvider.convertToken(token);
//        if(!jwtTokenProvider.validateToken(accessToken)) {
//            throw new JwtException("인증 만료");
//        }
//        return true;
//    }

    // 선생님이랑 같이 해보는 부분
    public Boolean isDuplicatedEmail(String email) {
        Boolean result = false;

        result = userMapper.findUserByEmail(email) != null;
        result = userMapper.getUserCountByEmail(email) > 0;

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean insertUser(SignUpReqDto signupReqDto) {
        Boolean result = false;
        User user = signupReqDto.toUser(passwordEncoder);
        result = userMapper.saveUser(user) > 0;
        return result;
    }

    public String signin(SignInReqDto signinReqDto) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(signinReqDto.getEmail(), signinReqDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        System.out.println("여기까지왔음!!!!");
        System.out.println(authentication.getName());

        Key kye = Keys.hmacShaKeyFor(Decoders.BASE64.decode("1UQJ1AHIvUfFlCwj4WAauoat9iaJN4nP3bolvD0Zhnw1ZLWwuG8rH7I06jQgGgWorvdHuRNSo5DXij3MIoFKRw=="));

        Date date = new Date(new Date().getTime() + (1000 * 60 * 60 * 24));
        String jwtToken = Jwts.builder()
                .claim("username", authentication.getName())
                .claim("auth", authentication.getAuthorities())
                .setExpiration(date)
                .signWith(kye, SignatureAlgorithm.HS256)
                .compact();
        System.out.println(jwtToken);
        return jwtToken;
    }

}
