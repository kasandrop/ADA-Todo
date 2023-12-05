package com.marcin.todo.data;

import com.marcin.todo.entity.Label;
import com.marcin.todo.entity.Task;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class LabelRepositoryTest {


    private static final Logger logger = LoggerFactory.getLogger(LabelRepositoryTest.class);
    @Autowired
    private LabelRepository labelRepository;

    @Test
    void findAllLabels() {

        logger.info("2.1 Verify that the repository correctly retrieves all labels.");
        List<Label> actual = (List<Label>) labelRepository.findAll();
        assertThat(actual).hasSize(3);
    }

    @Test
    void saveLabel() {

        logger.info("2.2 Verify that the repository correctly saves a new label.");

        Label expected = Label.builder().name("MyLabel11").build();

        List<Task> tasks = TaskFactory.createTasks(expected, 3);
        expected.setTasks(tasks);
        labelRepository.save(expected);

        Optional<Label> actual = labelRepository.findById(expected.getId());
        assertThat(actual).isPresent();

        assertThat(actual).get().hasFieldOrPropertyWithValue("name", "MyLabel11");
        assertThat(actual.get()).usingRecursiveComparison().ignoringFields("id").isEqualTo(expected);

    }

    @Test
    void findLabelById() {

        logger.info("2.3  Find Label by ID: Verify that the repository correctly retrieves a label by its ID.");
        List<Task> tasks = new ArrayList<>();

        Label expected = Label.builder().tasks(tasks).name("HOME").build();
        Optional<Label> foundLabel = labelRepository.findById(1);

        assertThat(foundLabel).isPresent();
        assertThat(foundLabel.get().getName()).isEqualTo(expected.getName());
    }

    @Test
    void deleteLabel() {
        logger.info("2.4 Delete a Label: Verify that the repository correctly deletes a label.");

        Optional<Label> foundLabel = labelRepository.findById(3);
        assertThat(foundLabel).isPresent();

        labelRepository.deleteById(3);

        Optional<Label> deletedLabel = labelRepository.findById(3);
        assertThat(deletedLabel).isNotPresent();


    }

    @Test
    void updateLabelWhenIdExists() {

        logger.info("2.5 Verify that when a label is created with an id that already exists, the existing label is updated and the total count of labels remains the same.");

        // Create a new label
        Label expected = Label.builder().name("MyLabel12").id(1).build();
        List<Task> tasks = TaskFactory.createTasks(expected, 2);
        expected.setTasks(tasks);


        // Get the current count of labels
        var countBeforeUpdate = labelRepository.count();
        labelRepository.save(expected);



        // Get the updated label
        Optional<Label> actual = labelRepository.findById(expected.getId());

        // Verify that the label was updated
        assertThat(actual).isPresent();
        assertThat(actual.get().getName()).isEqualTo("MyLabel12");

        // Verify that the total count of labels remains the same
        var countAfterUpdate = labelRepository.count();
        assertThat(countBeforeUpdate).isEqualTo(countAfterUpdate);
    }

    @Test
    void findLabelByName() {
        logger.info(" 2.6 Test Case: Verify that the repository correctly retrieves a label by its name.");


        // When: Retrieve the label by its name
        Label actual = labelRepository.findByName("HOUSEHOLD").orElseThrow(() -> new NoSuchElementException("Task not found"));

        // Then: Verify that the label was retrieved correctly
        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo("HOUSEHOLD");
        assertThat(actual.getId()).isEqualTo(3);
        assertThat(actual.getTasks()).size().isEqualTo(5);
    }


}
