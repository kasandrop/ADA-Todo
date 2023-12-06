package com.marcin.todo.data;


import com.marcin.todo.entity.Label;
import com.marcin.todo.entity.Task;
import com.marcin.todo.factories.TaskFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")

public class TaskRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(TaskRepositoryTest.class);
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private LabelRepository labelRepository;

    @Test
    void findTaskById() {
        logger.info("3.1 Test Case: Verify that the repository correctly retrieves a task by its ID.");


        Optional<Task>actual = taskRepository.findById(13);

        // Then: Verify that the task was retrieved correctly
        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("Task: 13");
        assertThat(actual.get().getDescription()).isEqualTo("Description for Task 13");
        assertThat(actual.get().getLabel().getId()).isEqualTo(3);
        assertThat(actual.get().isCompletion()).isEqualTo(false);
    }
    @Test
    void newTaskHasCompletionSetToFalse() {
        logger.info("3.2 Test Case: Verify that when a new task is created, its completion status is correctly set to false.");

        Label label=Label.builder().name("LastLabel").build();
        // Given: Create a new task
        Task task = Task.builder().name("MyTask").description("My last task created").build();
        //
        var stateOfCompletion=task.isCompletion();
        assertThat(stateOfCompletion).isFalse();

    }

    @Test
    void updateTaskWhenIdExists() {

        logger.info("3.3 Test case. Verify that when a task is created with an id that already exists, the existing task is updated and the total count of tasks remains the same.");

        // Create a new task
        Task expected = Task.builder().name("MyTask 100").description("This is a task to update").id(1).build();
        Optional<Label> label = labelRepository.findById(3);
        assertThat(label).isPresent();
        expected.setLabel(label.get());

        // Get the current count of tasks
        var countBeforeUpdate = taskRepository.count();
        taskRepository.save(expected);

        // Get the updated task
        Optional<Task> actual = taskRepository.findById(expected.getId());

        // Verify that the task was updated
        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("MyTask 100");
        assertThat(actual.get().getDescription()).isEqualTo("This is a task to update");
        assertThat(actual.get().getLabel().getId()).isEqualTo(3);

        // Verify that the total count of tasks remains the same
        var countAfterUpdate = taskRepository.count();
        assertThat(countBeforeUpdate).isEqualTo(countAfterUpdate);
    }

    @Test
    void updateTaskName() {

        logger.info("3.4 Verify that when a task’s name is updated, the change is correctly saved in the database.");


        Task task = taskRepository.findById(15).orElseThrow(() -> new NoSuchElementException("Task not found"));
        var name=task.getName();
        var newName="my old name was: "+name;
        task.setName(newName);
        taskRepository.save(task);

        Task updatedTask= taskRepository.findById(15).orElseThrow(() -> new NoSuchElementException("Task not found"));

        assertThat(updatedTask.getName()).isEqualTo("my old name was: Task: 15");
    }

    @Test
    void updateTaskCompletionStatus() {

        logger.info("3.5 Verify that when a task’s completion status is updated, the change is correctly saved in the database.");

        Task task = taskRepository.findById(15).orElseThrow(() -> new NoSuchElementException("Task not found"));
        var status=task.isCompletion();

        // I want to update the status from false to true
        //checking if is false
        assertThat(status).isFalse();
        //updating to true
        task.setCompletion(true);
        taskRepository.save(task);
        // Get the updated task
        Task updatedTask= taskRepository.findById(15).orElseThrow(() -> new NoSuchElementException("Task not found"));
        //verify
        assertThat(updatedTask.isCompletion()).isTrue();

    }

    @Test
    void findAllTasks() {

        logger.info("3.6 Verify that the repository correctly retrieves all records from Task Table.");

        // Get all tasks from the repository
        List<Task> tasks = (List<Task>) taskRepository.findAll();

        // Verify that the list contains the expected number of tasks
        assertThat(tasks.size()).isEqualTo(15);

    }
    @Test
    void findTaskByName() {

        logger.info("3.7 Find Task by Name: Verify that the repository correctly retrieves a task by its name. ");

        // Define the name of the task to find
        String taskName = "Task :1";

        // Get the task from the repository
        Optional<Task> taskOptional = taskRepository.findByName(taskName);

        // Verify that the task was found
        assertThat(taskOptional).isPresent();
        assertThat(taskOptional.get().getName()).isEqualTo(taskName);
    }

    @Test
    void saveTask() {

        logger.info("3.8 Create Task when all mandatory data is provided (the associated Label and a name of a task). Verify that the repository saves a task");

        // Get all tasks from the repository
        int tasksNumber =( (List<Task>) taskRepository.findAll()).size();
        // Define the existing Label
        Label label= Label.builder().name("HOUSEHOLD").id(3).build();
        List<Task> taskList= TaskFactory.createTasks(label,1);
        Task taskToSave= taskList.get(0);

        Task saved= taskRepository.save(taskToSave);

        // Get all tasks from the repository
        List<Task> tasksNewList = (List<Task>) taskRepository.findAll();
        assertThat(tasksNewList.size()).isEqualTo(tasksNumber+1);

    }






}
