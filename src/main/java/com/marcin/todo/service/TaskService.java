package com.marcin.todo.service;

import com.marcin.todo.data.TaskRepository;
import com.marcin.todo.entity.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);
        return tasks;
    }

    public Task getTask(int id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
    }

    public Task createTask(Task newTask) {
        return taskRepository.save(newTask);
    }

    public Task updateTask(Task updatedTask, int id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setName(updatedTask.getName());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompletion(updatedTask.isCompletion());
                    task.setLabel(updatedTask.getLabel());
                    return taskRepository.save(task);
                })
                .orElseGet(() -> {
                    updatedTask.setId(id);
                    return taskRepository.save(updatedTask);
                });
    }

    public void deleteTask(int id) {
        taskRepository.deleteById(id);
    }
}