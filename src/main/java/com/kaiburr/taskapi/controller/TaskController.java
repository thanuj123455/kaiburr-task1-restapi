package com.kaiburr.taskapi.controller;

import com.kaiburr.taskapi.model.Task;
import com.kaiburr.taskapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@CrossOrigin // Allow frontend CORS by default
public class TaskController {

    @Autowired
    private TaskRepository repo;

    @GetMapping
    public List<Task> getAll(@RequestParam(required = false) String id) {
        if (id != null) {
            return repo.findById(id).map(List::of).orElse(List.of());
        }
        return repo.findAll();
    }

    @PutMapping
    public Task upsertTask(@RequestBody Task task) {
        // Add command validation here if needed
        return repo.save(task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        repo.deleteById(id);
    }

    @GetMapping("/find")
    public List<Task> findByName(@RequestParam String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    @PutMapping("/{id}/execution")
    public Task executeTask(@PathVariable String id) throws Exception {
        Optional<Task> taskOpt = repo.findById(id);
        if (taskOpt.isEmpty()) throw new Exception("Task not found");
        Task task = taskOpt.get();

        // Minimal shell execution demo (Works on local, not for K8s part)
        String output;
        Instant start = Instant.now();
        try {
            Process proc = Runtime.getRuntime().exec(task.getCommand());
            output = new String(proc.getInputStream().readAllBytes());
            proc.waitFor();
        } catch (Exception e) {
            output = "Error: " + e.getMessage();
        }
        Instant end = Instant.now();

        Task.Execution exec = new Task.Execution();
        exec.setStartTime(start.toString());
        exec.setEndTime(end.toString());
        exec.setOutput(output);

        if (task.getExecutions() == null) {
            task.setExecutions(new java.util.ArrayList<>());
        }
        task.getExecutions().add(exec);

        return repo.save(task);
    }
}
