package com.example.ossystem.coordinate;


import com.example.ossystem.figure.exceptions.DataNotFoundException;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CoordinateService {

    private final CoordinatesRepository repository;

    public List<Coordinate> getAllCoordinates() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "position"));
    }

    public List<Coordinate> getAllCoordinatesSorted(boolean sortAsc) {
        val sorterDirection = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        return repository.findAll(Sort.by(sorterDirection, "position"));
    }

    public Coordinate createCoordinate(Coordinate coordinate) {
        var maxPosition = repository.getMaxPosition();
        if (maxPosition == null) maxPosition = 0;
        coordinate.setPosition(maxPosition + 1);

        return repository.save(coordinate);
    }

    @Transactional
    public Coordinate updateCoordinate(Long coordId, CoordinateInputDto newCoordData) {
        val coordinate = repository.findById(coordId)
                .orElseThrow(() -> new DataNotFoundException());

        coordinate.setX(newCoordData.getX());
        coordinate.setY(newCoordData.getY());

        val newPos = newCoordData.getPosition();
        val oldPos = coordinate.getPosition();
        if (newPos != oldPos) {
            if (newPos > oldPos) {
                repository.shiftSegmentUp(oldPos, newPos);
            } else {
                repository.shiftSegmentDown(oldPos, newPos);
            }
            coordinate.setPosition(newPos);
        }
        return repository.save(coordinate);
    }

    public String deleteCoordinate(Long coordId) {
        val coordinate = repository.findById(coordId)
                .orElseThrow(() -> new DataNotFoundException());
        if (coordinate.getPosition() < repository.getMaxPosition())
            repository.shiftDown(coordinate.getPosition());
        repository.delete(coordinate);
        return "Deleted";
    }

    public Optional<Coordinate> getCoordinateById(Long id) {
        return repository.findById(id);
    }
}
