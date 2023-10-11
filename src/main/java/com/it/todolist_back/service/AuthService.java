package com.it.todolist_back.service;

import com.it.todolist_back.dto.SignUpReqDto;
import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public Boolean isDuplicated(String email) {
        Boolean result = false;

        // null인지 확인 -> 재사용성이 높음
        result = userMapper.findUserByEmail(email) != null;

        // count횟수
        result = userMapper.getUserCountByEmail(email) > 0;

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public Boolean insertUser(SignUpReqDto signUpReqDto) {
        Boolean result = false;
        User user = signUpReqDto.toUser(passwordEncoder);
        result = userMapper.saveUser(user) > 0;

        return result;
    }
}
