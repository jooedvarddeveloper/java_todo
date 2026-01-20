package com.example.todo.service;

import com.example.todo.dto.request.TodoRequestDTO;
import com.example.todo.dto.response.TodoResponseDTO;
import com.example.todo.entity.TodoEntity;
import com.example.todo.entity.TodoStatusEntity;
import com.example.todo.enums.TodoStatus;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.TodoStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponseDTO> getTodos() {
        return todoRepository.findAll().stream().map(this::mapDTO).toList();
    }

    public TodoResponseDTO getTodo(Long id) {
        return this.mapDTO(findTodo(id).orElseThrow(this::throwTodoNotFound));
    }

    public Optional<TodoEntity> findTodo(Long id) {
        return todoRepository.findById(id);
    }

    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setDescription(todoRequestDTO.getDescription());
        todoEntity.setCompleted(todoRequestDTO.isCompleted());
        TodoEntity savedTodoEntity = this.todoRepository.save(todoEntity);
        return mapDTO(savedTodoEntity);
    }

    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO todoRequestDTO) {
        TodoEntity todoEntity = findTodo(id).orElseThrow(this::throwTodoNotFound);
        todoEntity.setStatus(todoRequestDTO.getStatus());
        todoEntity.setDescription(todoRequestDTO.getDescription());
        todoEntity.setCompleted(todoRequestDTO.isCompleted());
        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);
        return mapDTO(savedTodoEntity);
    }

    public TodoResponseDTO patchTodo(Long id, TodoRequestDTO todoRequestDTO) {
        TodoEntity todoEntity = findTodo(id).orElseThrow(this::throwTodoNotFound);

        if (todoRequestDTO.getDescription() != null) {
            todoEntity.setDescription(todoRequestDTO.getDescription());
        }

        todoEntity.setCompleted(todoRequestDTO.isCompleted());
        TodoEntity savedTodoEntity = todoRepository.save(todoEntity);
        return mapDTO(savedTodoEntity);
    }

    public ResponseEntity<Object> deleteTodo(Long id) {
        TodoEntity todoEntity = findTodo(id).orElseThrow(this::throwTodoNotFound);
        todoRepository.delete(todoEntity);
        return ResponseEntity.noContent().build();
    }

    private TodoResponseDTO mapDTO (TodoEntity todoEntity) {
        TodoStatus todoStatus = todoEntity.getStatus() != null ? todoEntity.getStatus().getStatus() : null;

        return TodoResponseDTO.builder()
                .id(todoEntity.getId())
                .description(todoEntity.getDescription())
                .completed(todoEntity.getCompleted())
                .status(todoStatus)
                .build();
    }

    private RuntimeException throwTodoNotFound () {
        return new RuntimeException("Todo not found!");
    }

}
