package ru.taskmanagment.controller;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.taskmanagment.payload.rq.TaskRq;
import ru.taskmanagment.payload.rs.TaskRs;
import ru.taskmanagment.service.TaskService;

import java.util.List;

import static ru.taskmanagment.util.RoleLocal.ADMIN;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    //@RolesAllowed({USER,ADMIN})
    public ResponseEntity<List<TaskRs>> getAllTasks() {
        List<TaskRs> tasks = taskService.getAllTasks().stream()
                .map(TaskRs::toTaskRs)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @RolesAllowed(ADMIN)
    public ResponseEntity<TaskRs> getTask(@PathVariable Long id) {
        TaskRs taskRs = taskService.getTaskById(id);
        return ResponseEntity.ok(taskRs);
    }

    @PostMapping
    //@RolesAllowed({USER, ADMIN})
    public ResponseEntity<TaskRs> createTask(@RequestBody TaskRq taskRq) {
        TaskRs taskRs = taskService.createTask(taskRq);
        return ResponseEntity.status(201).body(taskRs);
    }

    @PutMapping("/{id}")
    @RolesAllowed(ADMIN)
    public ResponseEntity<TaskRs> updateTask(@PathVariable Long id, @RequestBody TaskRq taskRq) {
        TaskRs updatedTask = taskService.updateTask(id, taskRq);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(ADMIN)
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        String response = taskService.deleteTask(id);
        return ResponseEntity.ok(response);
    }
}
