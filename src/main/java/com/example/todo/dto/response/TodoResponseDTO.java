package com.example.todo.dto.response;

import com.example.todo.enums.TodoStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@JsonPropertyOrder({"id", "description", "completed"})
public class TodoResponseDTO {
    private Long id;
    private String description;
    private Boolean completed;
    private TodoStatus status;
}
