package com.it.todolist_back.controller;

import com.it.todolist_back.dto.AddTodoReqDto;
import com.it.todolist_back.dto.UpdateTodoReqDto;
import com.it.todolist_back.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Path;

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

    @DeleteMapping("/todo/{todoId}")
    // @RequestHeader int todoId 굳이 header에 안넣어도 됨...
    public ResponseEntity<?> deleteTodo(@PathVariable int todoId) {
        return ResponseEntity.ok().body(todoService.deleteTodo(todoId));
    }

    @PutMapping("/todo/{todoId}")
    // 쌤은 Id, updateDto 둘다를 받도록
    public ResponseEntity<?> updateTodo(@PathVariable int todoId, @RequestBody UpdateTodoReqDto updateTodoReqDto) {
        return ResponseEntity.ok(todoService.updateTodo(todoId, updateTodoReqDto));
    }
}
