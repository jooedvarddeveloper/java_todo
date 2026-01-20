package com.example.todo.repository;

import com.example.todo.entity.TodoStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoStatusRepository extends JpaRepository<TodoStatusEntity, Long> {
}
