package com.marcin.todo.controllers;

import com.marcin.todo.entity.Label;
import com.marcin.todo.entity.Task;
import com.marcin.todo.service.TaskService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @MockBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTasks() throws Exception {

        String jsonList = """
        [{
            "id":1,
            "name":"Clean Car",
            "description":"Clean car in garage",
            "completion":false
          },
          {
            "id":2,
            "name":"Wash Dishes",
            "description":"Clean all dishes in kitchen",
            "completion":false
        }]""";

        Task cleanCar = Task.builder().id(1).name("Clean Car").description("Clean car in garage").completion(false).build();
        Task washDishes = Task.builder().id(2).name("Wash Dishes").description("Clean all dishes in kitchen").completion(false).build();

        Label labelMustExistAlways= Label.builder().id(12).name("washingMachine").build();

        cleanCar.setLabel(labelMustExistAlways);
        washDishes.setLabel(labelMustExistAlways);

        List<Task> tasks = Arrays.asList(cleanCar, washDishes);

        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonList));


    }

    @Test
    void getTaskById() throws Exception {

        String jsonList = """
                {
                    "id":1,
                    "name":"Clean Car",
                    "description":"Clean car in garage",
                    "completion":false
                  }""";

        int testID = 1;

        Label labelMustExistAlways= Label.builder().id(12).name("washingMachine").build();

        Task task = Task.builder().id(testID).name("Clean Car").description("Clean car in garage").completion(false).label(labelMustExistAlways).build();

        when(taskService.getTask(testID)).thenReturn(task);

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + testID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonList));
    }

    @Test
    void addTask() throws Exception {
        int testID = 1;

        String jsonList = """
                {
                    "id":1,
                    "name":"Clean Car",
                    "description":"Clean car in garage",
                    "completion":false
                  }""";

        String jsonWash= """
                {
                "id":2,
                "name":"Wash Dishes",
                "description":"Clean all dishes in kitchen",
                "completion":false
        }""";

        Label labelMustExistAlways= Label.builder().id(33).name("washingMachine").build();

        Task cleanCar = Task.builder()
                .id(testID)
                .name("Clean Car")
                .description("Clean car in garage")
                .completion(false)
                .label(labelMustExistAlways)
                .build();

        Task washDishes = Task.builder()
                .id(testID+1)
                .name("Wash Dishes")
                .description("Clean all dishes in kitchen")
                .completion(false)
                .label(labelMustExistAlways)
                .build();

        when(taskService.saveTask(any(Task.class))).thenReturn(cleanCar);


        ResultActions responseCar = mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonList));
        responseCar.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(cleanCar.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(cleanCar.getId())));

        when(taskService.saveTask(any(Task.class))).thenReturn(washDishes);
        ResultActions responseWash = mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWash));
        responseWash.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(washDishes.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(washDishes.getId())));
    }




    @Test
    public void deleteTask() throws Exception {
        int id = 1;

        doNothing().when(taskService).deleteTask(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted."));
        verify(taskService, Mockito.times(1)).deleteTask(id);
    }



}
