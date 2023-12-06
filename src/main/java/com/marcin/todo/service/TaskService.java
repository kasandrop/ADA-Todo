package com.marcin.todo.service;


import com.marcin.todo.data.TaskRepository;
import com.marcin.todo.entity.Label;
import com.marcin.todo.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelService labelService;

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public Task getTask(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
    }

    public Task saveTask(Task task) {
        if (task.getName() == null || task.getName().isEmpty()) {
            throw new IllegalArgumentException("Task creation failed: A name is required for each task.");
        }

        labelService.getLabel(task.getLabel().getId());
        return taskRepository.save(task);
    }

    public void deleteTask(int id) {
       taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
    }


}