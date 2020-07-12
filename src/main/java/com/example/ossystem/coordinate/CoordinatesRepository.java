package com.example.ossystem.coordinate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinate, Long> {

    @Query("select max(position) from Coordinate")
    Integer getMaxPosition();

    @Modifying
    @Transactional
    @Query("update Coordinate c set c.position = c.position - 1 where c.position > :oldPos and c.position <= :newPos")
    void shiftSegmentUp(@Param("oldPos") int oldPos, @Param("newPos") int newPos);

    @Modifying
    @Transactional
    @Query("update Coordinate c set c.position = c.position + 1 where c.position >= :newPos and c.position < :oldPos")
    void shiftSegmentDown(@Param("oldPos") int oldPos, @Param("newPos") int newPos);

    @Modifying
    @Transactional
    @Query("update Coordinate c set c.position = c.position - 1 where c.position > :removedPosition")
    void shiftDown(@Param("removedPosition") int removedPosition);
}
