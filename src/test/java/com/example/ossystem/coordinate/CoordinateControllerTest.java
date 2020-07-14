package com.example.ossystem.coordinate;

import com.example.ossystem.TestRunner;
import com.example.ossystem.figure.modelsAndDto.Figure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
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
public class CoordinateControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    CoordinatesRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void getAllCoordinates() throws Exception {
        Coordinate coordinate = new Coordinate(1, 2, 3);
        Coordinate coordinate2 = new Coordinate(3, 4, 5);
        repository.saveAndFlush(coordinate);
        repository.saveAndFlush(coordinate2);
        mockMvc.perform(get("/coordinates"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].x", is(2)))
                .andExpect(jsonPath("$[0].y", notNullValue()))
                .andExpect(jsonPath("$[1].x", is(4)))
                .andExpect(jsonPath("$[1].y", notNullValue()));

    }

    @Test
    public void getCoordinateById() throws Exception {
        Coordinate coordinate = new Coordinate(1, 2, 3);
        Long id = repository.save(coordinate).getId();
        mockMvc.perform(get("/coordinates/id")
                .contentType("application/json")
                .content(fromResource("json/create-coordinate.json")));
    }

    @Test
    public void createCoordinate() throws Exception {
        Coordinate coordinate = new Coordinate(1, 2, 3);
//        Long id = repository.save(coordinate).getId();
        mockMvc.perform(post("/coordinates")
                .contentType("application/json")
                .content(fromResource("json/create-coordinate.json")))
                .andExpect(status().isOk());

    }

    @Test
    public void updateCoordinate() throws Exception {

        Coordinate coordinate = new Coordinate(4, 5, 6);
        Long id = repository.save(coordinate).getId();

        mockMvc.perform(put("/coordinates/{id}", id)
                .contentType("application/json")
                .content(fromResource("json/create-coordinate.json")))
                .andExpect(status().isOk());

        assertThat(repository.findById(id).get().getPosition()).isEqualTo(1);
    }

    @Test
    public void deleteCoordinate() throws Exception {
        Long id = repository.save(new Coordinate(1, 2, 3)).getId();

        mockMvc.perform(delete("/coordinates/{id}", id))
                .andExpect(status().isNoContent());

        assertTrue(repository.findById(id).isEmpty());
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