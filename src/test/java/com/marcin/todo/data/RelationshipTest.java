package com.marcin.todo.data;

import com.marcin.todo.entity.Label;
import com.marcin.todo.entity.Task;
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
public class RelationshipTest {
    private static final Logger logger = LoggerFactory.getLogger(RelationshipTest.class);
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private LabelRepository labelRepository;



    @Test
    void createNewTask() {

        logger.info("4.1 Verify that a new task can be created.");

        // Create a new task
        Task expected = Task.builder().name("NewTask").description("This is a new task").build();
        Label label = labelRepository.findById(2).orElseThrow(() -> new NoSuchElementException("Task not found"));
        expected.setLabel(label);
        Task task =taskRepository.save(expected);

        // Save the task to the repository
        Task savedTask=taskRepository.findById(task.getId()).orElseThrow(() -> new NoSuchElementException("Task not found"));

        // Verify that the task was saved
        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getId()).isEqualTo(task.getId());
        assertThat(savedTask.getName()).isEqualTo(expected.getName());
        assertThat(savedTask.getDescription()).isEqualTo(expected.getDescription());
        assertThat(savedTask.isCompletion()).isEqualTo(expected.isCompletion());
        assertThat(savedTask.getLabel()).isEqualTo(expected.getLabel());
    }

    @Test

    void deleteTasksWhenLabelIsDeleted() {

        logger.info(" 4.2 Verify that when a label is deleted, all its associated tasks are also deleted.");

        // Get a label with associated tasks
        Label label = labelRepository.findById(1).orElseThrow(() -> new NoSuchElementException("Label not found"));

        List<Task> tasks= label.getTasks();

        // Delete the label
        labelRepository.delete(label);

        // Verify that the tasks associated with the label are also deleted
        for (Task task : tasks) {
            Optional<Task> actual = taskRepository.findById(task.getId());
            assertThat(actual).isNotPresent();
        }
    }
    @Test
    void taskReferencesItsLabel() {

        logger.info(" 4.3 Verify that when a task is retrieved by its ID, it correctly references its associated label.");

        //  the ID of the task to find
        int taskId = 1;

        // Get the task from the repository
        Optional<Task> taskOptional = taskRepository.findById(taskId);

        // Verify that the task was found and it references its label
        assertThat(taskOptional).isPresent();
        Task task = taskOptional.get();
        assertThat(task.getLabel()).isNotNull();
        assertThat(task.getLabel().getId()).isEqualTo(1);
    }

    @Test
    void labelNotDeletedWhenAllTasksAreDeleted() {

        logger.info(" 4.4 Verify that when all tasks of a label are deleted, the label itself is not deleted.");

        // Get a label with associated tasks
        Label label = labelRepository.findById(1).orElseThrow(() -> new NoSuchElementException("Label not found"));

        // Get the tasks of the label and delete them
        List<Task> tasks = label.getTasks();
        taskRepository.deleteAll(tasks);

        // Verify that the label still exists
        Optional<Label> labelOptional = labelRepository.findById(label.getId());
        assertThat(labelOptional).isPresent();
    }

    @Test
    void findTasksByLabel() {

        logger.info(" 4.5 Verify that the repository correctly retrieves all tasks associated with a specific label.");

        // Get a label
        Label label = labelRepository.findById(2).orElseThrow(() -> new NoSuchElementException("Label not found"));

        // Get the tasks of the label
        List<Task> tasks = label.getTasks();

        assertThat(tasks.size()).isEqualTo(5);

    }



}
