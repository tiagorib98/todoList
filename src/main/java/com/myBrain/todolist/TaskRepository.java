package com.myBrain.todolist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository // Avisa o Spring que isto é a nossa ponte para a Base de Dados
public interface TaskRepository extends JpaRepository<TaskM, Long> {

    List<TaskM> findAllByOrderByDueDateAscPriorityAsc();
    List<TaskM> findByFinishedFalseOrderByDueDateAscPriorityAsc();
    List<TaskM> findByDueDateOrderByPriorityAsc(LocalDate dueDate);
}
