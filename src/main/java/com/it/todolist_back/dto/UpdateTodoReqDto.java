package com.it.todolist_back.dto;

import com.it.todolist_back.entity.Todo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateTodoReqDto {
    private String updateContent;

    //    private int todoId;
//    private String content;

//    public Todo toTodoEntity(UpdateTodoReqDto updateTodoReqDto) {
//        return Todo.builder()
//                .todoId(todoId)
//                .content(content)
//                .build();
//    }
}
