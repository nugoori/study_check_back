package com.it.todolist_back.controller;

import com.it.todolist_back.dto.AddTodoReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoController {

    @PostMapping("/todo")
    public ResponseEntity<?> addTodo(@RequestBody AddTodoReqDto addTodoReqDto) {
        System.out.println(addTodoReqDto);

        return ResponseEntity.ok().body(null);
    }
}
