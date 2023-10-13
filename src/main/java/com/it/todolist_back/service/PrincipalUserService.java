package com.it.todolist_back.service;

import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.UserMapper;
import com.it.todolist_back.security.PrincipalUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalUserService implements UserDetailsService {

    private final UserMapper userMapper;

    // authenticationManagerBuilder에 의해 호출됨
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//
//        User user = userMapper.findUserByEmail(email);
//        if(user != null) {
//            return new PrincipalUser(user);
//        }
//
//        throw new UsernameNotFoundException("입력하신 사용자 정보가 존재하지 않습니다. 다시 입력해주세요.");
//    }

    // 쌤이랑 한 부분
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername<email>: " + email);
        User user = userMapper.findUserByEmail(email);

        if(user == null) {
            return null;
        }

        return new PrincipalUser(user);
    }
}
