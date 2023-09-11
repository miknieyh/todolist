package com.example.todolist.dto;

import com.example.todolist.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String userId;
    private String title;
    private boolean done;
    public TodoDTO(final TodoEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }
    public static TodoEntity todoEntity(final TodoDTO dto){
        return TodoEntity.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .done(dto.done)
                .build();
    }
}
