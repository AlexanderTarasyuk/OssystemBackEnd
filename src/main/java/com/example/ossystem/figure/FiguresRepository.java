package com.example.ossystem.figure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiguresRepository extends JpaRepository<Figure, Integer> {
}