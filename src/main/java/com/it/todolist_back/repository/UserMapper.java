package com.it.todolist_back.repository;

import com.it.todolist_back.dto.SignUpReqDto;
import com.it.todolist_back.entity.Todo;
import com.it.todolist_back.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User findUserByEmail(String email);
    public Integer getUserCountByEmail(String email);
    public Integer saveUser(User user);

}
