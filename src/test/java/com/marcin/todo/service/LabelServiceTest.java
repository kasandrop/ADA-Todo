package com.marcin.todo.service;

import com.marcin.todo.data.ExceptionsTest;
import com.marcin.todo.data.LabelRepository;
import com.marcin.todo.data.TaskRepository;
import com.marcin.todo.entity.Label;
import com.marcin.todo.factories.TaskFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class LabelServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionsTest.class);
    @InjectMocks
    private LabelService labelService;
    @Mock
    private LabelRepository labelRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskService taskService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetLabel() {

        logger.info("2.3 Find Label by ID: Verify the correct label retrieval");
        Label label = Label.builder().name("Label1").id(1).build();
        label.setTasks(TaskFactory.createTasks(label, 3));

        when(labelRepository.findById(1)).thenReturn(Optional.of(label));

        Label found = labelService.getLabel(1);

        assertEquals(label.getName(), found.getName());
        verify(labelRepository, times(1)).findById(1);
    }

    @Test
    public void testSaveLabel() {

        logger.info("2.2 Find Label by ID: Verify the label is saved");
        Label label = Label.builder().name("Label2").id(2).build();
        label.setTasks(TaskFactory.createTasks(label, 3));

        when(labelRepository.existsByName(label.getName())).thenReturn(false);
        when(labelRepository.save(label)).thenReturn(label);
        when(labelRepository.findById(2)).thenReturn(Optional.of(label));

        Label saved = labelService.saveLabel(label);

        assertEquals(label.getName(), saved.getName());
        assertEquals(label.getTasks().size(), 3);
        verify(labelRepository, times(1)).save(label);
    }

    @Test
    public void testSaveLabelWithExistingName() {

        logger.info("5.7  Verify that the service correctly raises an exception when trying to create a label with a name that is not unique.");

        Label label = Label.builder().name("Label2").id(2).build();
        label.setTasks(TaskFactory.createTasks(label, 3));

        when(labelRepository.existsByName(label.getName())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> labelService.saveLabel(label));
        //we checking that the service didnt try to save the Label
        verify(labelRepository, times(0)).save(label);
    }

    @Test
    public void testDeleteLabel() {

        logger.info(" 2.4 Delete a Label: Verify that the repository correctly deletes a label. ");
        Label label = Label.builder().name("Label2").id(1).build();
        label.setTasks(TaskFactory.createTasks(label, 3));
        int id = 1;

        when(labelRepository.findById(id)).thenReturn(Optional.of(label));
        doNothing().when(labelRepository).deleteById(id);

        labelService.deleteLabel(id);

        verify(labelRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteNonExistingLabel() {
        int id = 1;

        logger.info("5.1  Raise Exception When Deleting Non-Existing Label: Verify ");
        when(labelRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> labelService.deleteLabel(id));
        verify(labelRepository, times(1)).findById(id);
    }


    @Test
    public void testSaveLabelWithoutName() {
        logger.info("5.3  Raise Exception When Creating/saving Label Without Name:");
        Label label = Label.builder().build();

        assertThrows(IllegalArgumentException.class, () -> labelService.saveLabel(label));
        Label labelWithId = Label.builder().id(2).build();
        assertThrows(IllegalArgumentException.class, () -> labelService.saveLabel(labelWithId));


        verify(labelRepository, times(0)).save(label);
        verify(labelRepository, times(0)).save(labelWithId);
    }





}
