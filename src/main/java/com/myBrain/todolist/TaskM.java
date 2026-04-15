package com.myBrain.todolist;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class TaskM {
    // 1. Os Atributos (as características da tarefa)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private boolean finished;
    private LocalDate dueDate;
    private Integer priority;

    // 2. O Construtor (o método que "dá vida" a uma nova tarefa)
    public TaskM() {

    }

    public TaskM(String description, LocalDate dueDate, Integer priority) {
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.finished = false;
    }

    // 3. Getters e Setters (métodos para ler e alterar os atributos de forma segura)

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
