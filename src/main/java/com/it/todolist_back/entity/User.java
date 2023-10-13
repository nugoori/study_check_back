package com.it.todolist_back.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private Integer userId;
    private String email;
    private String password;
    private String username;
    private List<Authority> authorities;

        // security에서 사용할 SimpleGrantedAuthority객체에 다시 담아줌
        public List<SimpleGrantedAuthority> toGrantedAuthorityList() {
            List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                    new ArrayList<>();
            // java stream ?
            authorities.forEach(authority ->
                    simpleGrantedAuthorities.add(
                            new SimpleGrantedAuthority(authority.getRole()
                                    .getRoleName())));
            return simpleGrantedAuthorities;
    }
}
