package com.example.ossystem.figure;

import com.example.ossystem.figure.modelsAndDto.Figure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiguresRepository extends JpaRepository<Figure, Integer> {

    Optional<Figure> findByFirstName(String firstName);

}