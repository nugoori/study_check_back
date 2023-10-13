package com.it.todolist_back.service;

import com.it.todolist_back.dto.AddTodoReqDto;
import com.it.todolist_back.dto.GetTodoListRespDto;
import com.it.todolist_back.entity.Todo;
import com.it.todolist_back.entity.User;
import com.it.todolist_back.repository.TodoMapper;
import com.it.todolist_back.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoMapper todoMapper;

    public Boolean addTodo(AddTodoReqDto addTodoReqDto) {
        // JwtAuthenticationFilter에서 인증 받으려고 넣어둔 Authentication을 다시 꺼내서 email을 가져옴
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Todo todo = Todo.builder()
                .content(addTodoReqDto.getContent())
                .email(email)
                .build();

        return todoMapper.saveTodo(todo);
    }

    public List<GetTodoListRespDto> getTodoList() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<GetTodoListRespDto> getTodoListRespDtos = new ArrayList<>();

        todoMapper.getTodoListByEmail(email).forEach(todo -> {
            getTodoListRespDtos.add(todo.toTodoListRespDto());
        });

        return getTodoListRespDtos;
    }

}
