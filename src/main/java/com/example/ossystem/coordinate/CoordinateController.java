package com.example.ossystem.coordinate;


import lombok.AllArgsConstructor;
import lombok.val;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor

public class CoordinateController {
    private final CoordinateService coordService;

    @GetMapping("/coordinates")
    public List<Coordinate> getAllCoordinates() {
        return coordService.getAllCoordinates();
    }

    @GetMapping("/coordinates/sort")
    public List<Coordinate> getAllCoordinatesSorted(@RequestParam(name = "sortAsc") boolean sortAsc) {
        return coordService.getAllCoordinatesSorted(sortAsc);
    }

    @GetMapping("/coordinates/{id}")
    public Coordinate getCoordinateById(@PathVariable(value = "id") Long id) {
        val maybeCoordinate = coordService.getCoordinateById(id);
        return maybeCoordinate.orElseThrow(() -> new NoSuchCoordinate(id));
    }

    @PostMapping("/coordinates")
    public ResponseEntity<?> createCoordinate(@Valid @RequestBody CoordinateInputDto coordinateInputDto) {
        val coordinate =
                new Coordinate(coordinateInputDto.getPosition(), coordinateInputDto.getX(), coordinateInputDto.getY());
        coordService.createCoordinate(coordinate);
        return ResponseEntity.ok().build();
    }

    @Retryable(StaleObjectStateException.class)
    @PutMapping("/coordinates/{id}")
    public ResponseEntity<?> updateCoordinate(@PathVariable(value = "id") Long coordId,
                                              @Valid @RequestBody CoordinateInputDto newCoordData) {
        coordService.updateCoordinate(coordId, newCoordData);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/coordinates/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCoordinate(@PathVariable(value = "id") Long id) {
        coordService.deleteCoordinate(id);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public void noSuchCoordinate(NoSuchCoordinate ex) {
    }
}
