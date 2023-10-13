package com.it.todolist_back.repository;

import com.it.todolist_back.entity.Todo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    public Boolean saveTodo(Todo todo);
    public List<Todo> getTodoListByEmail(String email);

}
