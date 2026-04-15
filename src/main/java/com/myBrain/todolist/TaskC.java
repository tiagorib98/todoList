package com.myBrain.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TaskC {
    @Autowired
    private TaskRepository repository;

    // -------------------------------------------------------------------------
    // Ação 1: Mostrar a página inicial
    // -------------------------------------------------------------------------
    @GetMapping("/") // Quando o utilizador for ao endereço principal do site (/)
    public String showTasks(Model model,
                            @RequestParam(required = false) Boolean hide,
                            @RequestParam(required = false) Boolean today) {
        // Se o utilizador clicou no filtro de HOJE
        if (today != null && today == true) {
            // LocalDate.now() vai buscar o dia exato em que estamos!
            model.addAttribute("tasks", repository.findByDueDateOrderByPriorityAsc(LocalDate.now()));
            model.addAttribute("isTodayFilter", true);
            model.addAttribute("isFiltered", false);

        }
        // Se o utilizador clicou no filtro para ESCONDER CONCLUÍDAS
        else if (hide != null && hide == true) {
            model.addAttribute("tasks", repository.findByFinishedFalseOrderByDueDateAscPriorityAsc());
            model.addAttribute("isFiltered", true);
            model.addAttribute("isTodayFilter", false);

        }
        // Caso contrário (Mostrar Todas)
        else {
            model.addAttribute("tasks", repository.findAllByOrderByDueDateAscPriorityAsc());
            model.addAttribute("isFiltered", false);
            model.addAttribute("isTodayFilter", false);
        }
        return "index";
    }

    // -------------------------------------------------------------------------
    // Ação 2: Adicionar uma nova tarefa
    // -------------------------------------------------------------------------
    @PostMapping("/add")
    public String addTask(@RequestParam String description,
                          @RequestParam LocalDate dueDate,
                          @RequestParam Integer priority) {

        TaskM newTask = new TaskM(description, dueDate, priority);

        repository.save(newTask);

        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id, HttpServletRequest request) {
        repository.deleteById(id);
        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable Long id, HttpServletRequest request) {
        TaskM task = repository.findById(id).orElse(null);

        if (task != null) {
            task.setFinished(!task.isFinished());
            repository.save(task);
        }

        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/");
    }

    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id,
                           @RequestParam String description,
                           @RequestParam LocalDate dueDate,
                           @RequestParam Integer priority,
                           HttpServletRequest request) { // Usamos o request para o truque da memória do filtro!

        // 1. Procuramos a tarefa antiga na base de dados
        TaskM task = repository.findById(id).orElse(null);

        if (task != null) {
            // 2. Substituímos os dados velhos pelos novos
            task.setDescription(description);
            task.setDueDate(dueDate);
            task.setPriority(priority);

            // 3. Guardamos as alterações
            repository.save(task);
        }

        // 4. Voltamos para o ecrã onde estávamos (mantendo os filtros!)
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

}
