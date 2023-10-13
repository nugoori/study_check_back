package com.it.todolist_back.dto;

import lombok.Data;

@Data
public class SignInReqDto {
    private String email;
    private String password;
}
