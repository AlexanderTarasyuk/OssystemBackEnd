package com.example.ossystem.figure;

import com.example.ossystem.TestRunner;
import com.example.ossystem.coordinate.Coordinate;
import com.example.ossystem.coordinate.CoordinatesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@TestRunner
public class FigureRepositoryTest {

    @MockBean
    private TestEntityManager entityManager;

    @MockBean
    private CoordinatesRepository repository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        Coordinate coordinate = new Coordinate(1, 2, 3);
        repository.saveAndFlush(coordinate);

        // when
        Coordinate found = repository.findByPosition(1);

        // then
        assertThat(coordinate)
                .isEqualTo(found);
    }


}
