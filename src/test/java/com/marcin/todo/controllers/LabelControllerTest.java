package com.marcin.todo.controllers;

import com.marcin.todo.data.LabelRepository;
import com.marcin.todo.entity.Label;
import com.marcin.todo.service.LabelService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LabelController.class)
@AutoConfigureMockMvc(addFilters = false)

class LabelControllerTest {


    @MockBean
    private LabelService labelService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllLabels() throws Exception {

        String jsonList = """
        [{
            "id":1,
            "name":"Home"
        },
        {
            "id":2,
            "name":"Work"
        }]
        """;

        Label home = Label.builder().id(1).name("Home").build();
        Label work = Label.builder().id(2).name("Work").build();
        List<Label> labels = Arrays.asList(home, work);

        when(labelService.getAllLabels()).thenReturn(labels);

        mockMvc.perform(MockMvcRequestBuilders.get("/labels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonList));
    }

    @Test
    public void getLabelById() throws Exception {

        String jsonHome ="""
        {
            "id":1,
            "name":"Home"
        }
        """;
        int id = 1;
        Label label = Label.builder().id(id).name("Home").build();

        when(labelService.getLabel(id)).thenReturn(label);

        mockMvc.perform(MockMvcRequestBuilders.get("/labels/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonHome));
    }

    @Test
    public void getLabelByIdNotFound() throws Exception {
        int id = 1;

        when(labelService.getLabel(id)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/labels/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addLabel() throws Exception {

        String jsonHome ="""
        {
            "id":1,
            "name":"Home"
        }
        """;

        String jsonWork ="""
        {
            "id":2,
            "name":"Work"
        }
        """;

        Label labelHome = Label.builder().id(1).name("Home").build();
        Label labelWork = Label.builder().id(2).name("Work").build();

        when(labelService.createLabel(labelHome)).thenReturn(labelHome);
        when(labelService.createLabel(labelWork)).thenReturn(labelWork);

        ResultActions responseHome = mockMvc.perform(MockMvcRequestBuilders.post("/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonHome));
        responseHome.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(labelHome.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(labelHome.getId())));

        ResultActions responseWork = mockMvc.perform(MockMvcRequestBuilders.post("/labels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWork));
        responseWork.andExpect(status().isCreated())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(labelWork.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(labelWork.getId())));


    }

    @Test
    public void deleteLabel() throws Exception {
        int id = 1;

        doNothing().when(labelService).deleteLabel(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/labels/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Label deleted."));
    }

}