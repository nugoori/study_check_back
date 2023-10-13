package com.it.todolist_back.controller;

import com.it.todolist_back.dto.AddTodoReqDto;
import com.it.todolist_back.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<?> addTodo(@RequestBody AddTodoReqDto addTodoReqDto) {

        //        SecurityContextHolder.getContext().getAuthentication().getName(); // filter를 거쳐 Authentication 객체가 들어있기 때문에 안의 사용자 이름을 가져올 수 있음
        return ResponseEntity.ok().body(todoService.addTodo(addTodoReqDto));
    }

    @GetMapping("/todo/list")
    public ResponseEntity<?> getTodoList() {

        return ResponseEntity.ok().body(todoService.getTodoList());
    }
}
