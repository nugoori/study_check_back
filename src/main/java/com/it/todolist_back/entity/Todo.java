package com.it.todolist_back.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {
    private int todoId;
    private int userId;
    private String todoContent;
}
