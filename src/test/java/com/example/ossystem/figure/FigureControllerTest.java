package com.example.ossystem.figure;

import com.example.ossystem.TestRunner;
import com.example.ossystem.figure.modelsAndDto.Figure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestRunner
@DirtiesContext
public class FigureControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    FiguresRepository figuresRepository;

    @Before
    public void setUp() throws Exception {
        figuresRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        figuresRepository.deleteAll();
    }

    @Test
    public void getFiguresPaginatedList() throws Exception {
        Figure figure1 = new Figure("circle");
        Figure figure2 = new Figure("square");
        figuresRepository.saveAndFlush(figure1);
        figuresRepository.saveAndFlush(figure2);
        mockMvc.perform(get("/figures"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is("circle")))
                .andExpect(jsonPath("$[0].id", notNullValue()))
                .andExpect(jsonPath("$[1].firstName", is("square")))
                .andExpect(jsonPath("$[1].id", notNullValue()));
    }

    @Test
    public void getFigureById() throws Exception {
        Figure figure = new Figure("circle");
        Integer id = figuresRepository.save(figure).getId();
        mockMvc.perform(get("/figures/id")
                .contentType("application/json")
                .content(fromResource("json/create-figure.json")));
    }

    @Test
    public void createFigure() throws Exception {

        Figure figure = new Figure("circle");
        figuresRepository.save(figure);
        mockMvc.perform(post("/figures")
                .contentType("application/json")
                .content(fromResource("json/create-figure.json")))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateFigure() throws Exception {
        Figure figure = new Figure("square");
        Integer id = figuresRepository.save(figure).getId();

        mockMvc.perform(put("/figures/{id}", id)
                .contentType("application/json")
                .content(fromResource("json/create-figure.json")))
                .andExpect(status().isOk());

        assertThat(figuresRepository.findById(id).get().getFirstName()).isEqualTo("circle");
    }

    @Test
    public void deleteFigure() throws Exception {
        Integer id = figuresRepository.save(new Figure(1, "circle")).getId();

        mockMvc.perform(delete("/figures/{id}", id))
                .andExpect(status().isNoContent());

        assertTrue(figuresRepository.findById(id).isEmpty());
    }

    public String fromResource(String path) {
        try {
            File file = ResourceUtils.getFile("classpath:" + path);
            return Files.readString(file.toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}