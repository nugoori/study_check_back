package com.it.todolist_back.repository;

import com.it.todolist_back.entity.Todo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TodoMapper {
    public int saveTodo(Todo todo);
    public List<Todo> getTodoListByEmail(String email);
    public int removeTodo(int todoId);
    public int updateTodo(Todo todo);

}
