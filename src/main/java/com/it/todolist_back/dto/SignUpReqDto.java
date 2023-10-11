package com.it.todolist_back.dto;

import com.it.todolist_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SignUpReqDto {

//    @Pattern(regexp = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\\\.[a-z]+$")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$", message = "숫자, 문자 중 반드시 하나를 포함 해주세요")
    private String password;

    @Pattern(regexp = "^[가-힣]*$", message = "한글만 입력이 가능합니다.")
    private String username;

    public User toUser(BCryptPasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .username(username)
                .build();
    }

}
