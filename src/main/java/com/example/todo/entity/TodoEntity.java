package com.example.todo.entity;

import com.example.todo.enums.TodoStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "completed", nullable = true)
    private Boolean completed;

    @OneToOne(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private TodoStatusEntity status;

    @PrePersist
    void onCreate() {
        setStatus(TodoStatus.CREATED);
    }

    public void setStatus(TodoStatus newStatus) {
        if (status == null) {
            TodoStatusEntity todoStatusEntity = new TodoStatusEntity();
            todoStatusEntity.setStatus(newStatus);
            todoStatusEntity.setTodo(this);
            this.status = todoStatusEntity;
        }

        else {
            this.status.setStatus(newStatus);
        }
    }
}
