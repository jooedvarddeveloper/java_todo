package com.example.todo.dto.request;

import com.example.todo.enums.TodoStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDTO {

    @NotNull(message = "Description cannot be nulL!")
    private String description;

    private boolean completed;
    private TodoStatus status;
}
