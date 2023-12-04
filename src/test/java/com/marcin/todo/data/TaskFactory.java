package com.marcin.todo.data;

import com.marcin.todo.entity.Label;
import com.marcin.todo.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskFactory {

    public static List<Task> createTasks(Label label, int taskCount){
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            Task task = Task.builder()
                    .name("Task " + (i+1))
                    .description("Description for Task " + (i+1))
                    .completion(false)
                    .label(label)
                    .build();
            tasks.add(task);
        }
        return tasks;
    }
}
