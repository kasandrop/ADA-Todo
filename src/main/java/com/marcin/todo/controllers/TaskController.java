package com.marcin.todo.controllers;

import com.marcin.todo.entity.Task;
import com.marcin.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController( TaskService taskService) {
        this.taskService = taskService;
    }

    //to list all tasks
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = new ArrayList<>(taskService.getAllTasks());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }


    @GetMapping("/tasks/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable(value = "id") int id) {
        try{
            Task task = taskService.getTask(id);
            return new ResponseEntity<>(task, HttpStatus.OK);
        }catch(IllegalArgumentException ie){
            return new ResponseEntity<>("Invalid task Id:" + id, HttpStatus.NOT_FOUND);
        }


    }


    //to add a taSK
    @PostMapping("/tasks")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task savedTask= taskService.saveTask(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }



    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(value = "id") int id) {
        taskService.deleteTask(id);
        return new ResponseEntity<>("Task deleted.", HttpStatus.OK);
    }
}
