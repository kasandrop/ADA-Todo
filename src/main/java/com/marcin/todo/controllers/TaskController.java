package com.marcin.todo.controllers;

import com.marcin.todo.data.TaskRepository;
import com.marcin.todo.entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //to list all tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    //to get a task by id
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable(value = "id") int id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }


    //to add a taSK
    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        return new ResponseEntity<>(taskRepository.save(task), HttpStatus.CREATED);
    }



    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(value = "id") int id) {
        taskRepository.deleteById(id);
        return new ResponseEntity<>("Task deleted.", HttpStatus.OK);
    }
}
