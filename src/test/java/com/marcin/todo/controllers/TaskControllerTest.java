package com.marcin.todo.controllers;

import com.marcin.todo.data.TaskRepository;
import com.marcin.todo.entity.Task;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @MockBean
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllTasks() throws Exception {

        String jsonList = """
        [{
            "id":1,
            "name":"Clean Car",
            "description":"Clean car in garage",
            "dueDate":"2024-01-01",
            "completion":false
          },
          {
            "id":2,
            "name":"Wash Dishes",
            "description":"Clean all dishes in kitchen",
            "dueDate":"2024-02-02",
            "completion":true
        }]""";

        Task cleanCar = Task.builder().id(1).name("Clean Car").description("Clean car in garage").dueDate(LocalDate.parse("2024-01-01")).completion(false).build();
        Task washDishes = Task.builder().id(2).name("Wash Dishes").description("Clean all dishes in kitchen").dueDate(LocalDate.parse("2024-02-02")).completion(true).build();
        List<Task> tasks = Arrays.asList(cleanCar, washDishes);

        when(taskRepository.findAll()).thenReturn(tasks);

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
                    "dueDate":"2024-01-01",
                    "completion":false
                  }""";

        int testID = 1;
        Task task = Task.builder().id(testID).name("Clean Car").description("Clean car in garage").dueDate(LocalDate.parse("2024-01-01")).completion(false).build();

        when(taskRepository.findById(testID)).thenReturn(Optional.of(task));

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + testID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonList));
    }

    @Test
    void getTaskByIdNotFound() throws Exception {
        int id = 1;

        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addTask() throws Exception {

        String jsonCar ="""
                {
                    "id":1,
                    "name":"Clean Car",
                    "description":"Clean car in garage",
                    "dueDate":"2024-01-01",
                    "completion":false
                }""";

        String jsonWash = """
                {
                    "id":2,
                    "name":"Wash Dishes",
                    "description":"Clean all dishes in kitchen",
                    "dueDate":"2024-02-02",
                    "completion":true
                }
                """;

        Task cleanCar = Task.builder().id(1).name("Clean Car").description("Clean car in garage").dueDate(LocalDate.parse("2024-01-01")).completion(false).build();
        Task washDishes = Task.builder().id(2).name("Wash Dishes").description("Clean all dishes in kitchen").dueDate(LocalDate.parse("2024-02-02")).completion(true).build();

        when(taskRepository.save(cleanCar)).thenReturn(cleanCar);
        when(taskRepository.save(washDishes)).thenReturn(washDishes);

        ResultActions responseCar = mockMvc.perform(MockMvcRequestBuilders.post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCar));
        responseCar.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(cleanCar.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(cleanCar.getId())));

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

        doNothing().when(taskRepository).deleteById(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Task deleted."));
    }



}
