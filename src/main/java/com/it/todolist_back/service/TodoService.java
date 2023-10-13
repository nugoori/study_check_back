package com.it.todolist_back.service;

import com.it.todolist_back.entity.Todo;
import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserMapper userMapper;

//    public Boolean insertTodo() {
//        User user = new User();
//        user.getUserId();
//        Todo todo = new Todo();
//        todo.getTodoContent();
//
//        if(userMapper.saveTodo(user.getUserId(), todo.getTodoContent()) > 0) {
//            return true;
//        }
//    }

}
