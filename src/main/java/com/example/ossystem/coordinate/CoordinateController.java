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

/**
 * The type Coordinate controller.
 */
@RestController
@CrossOrigin(origins = "${general.port}")
@AllArgsConstructor

public class CoordinateController {
    private final CoordinateService coordService;

    /**
     * Gets all coordinates.
     *
     * @return the all coordinates
     */
    @GetMapping("/coordinates")
    public List<Coordinate> getAllCoordinates() {
        return coordService.getAllCoordinates();
    }

    /**
     * Gets all coordinates sorted.
     *
     * @param sortAsc the sort asc
     * @return the all coordinates sorted
     */
    @GetMapping("/coordinates/sort")
    public List<Coordinate> getAllCoordinatesSorted(@RequestParam(name = "sortAsc") boolean sortAsc) {
        return coordService.getAllCoordinatesSorted(sortAsc);
    }

    /**
     * Gets coordinate by id.
     *
     * @param id the id
     * @return the coordinate by id
     */
    @GetMapping("/coordinates/{id}")
    public Coordinate getCoordinateById(@PathVariable(value = "id") Long id) {
        val maybeCoordinate = coordService.getCoordinateById(id);
        return maybeCoordinate.orElseThrow(() -> new NoSuchCoordinate(id));
    }

    /**
     * Create coordinate response entity.
     *
     * @param coordinateInputDto the coordinate input dto
     * @return the response entity
     */
    @PostMapping("/coordinates")
    public ResponseEntity<?> createCoordinate(@Valid @RequestBody CoordinateInputDto coordinateInputDto) {
        val coordinate =
                new Coordinate(coordinateInputDto.getPosition(), coordinateInputDto.getX(), coordinateInputDto.getY());
        coordService.createCoordinate(coordinate);
        return ResponseEntity.ok().build();
    }

    /**
     * Update coordinate response entity.
     *
     * @param coordId      the coord id
     * @param newCoordData the new coord data
     * @return the response entity
     */
    @Retryable(StaleObjectStateException.class)
    @PutMapping("/coordinates/{id}")
    public ResponseEntity<?> updateCoordinate(@PathVariable(value = "id") Long coordId,
                                              @Valid @RequestBody CoordinateInputDto newCoordData) {
        coordService.updateCoordinate(coordId, newCoordData);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete coordinate.
     *
     * @param id the id
     */
    @DeleteMapping("/coordinates/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCoordinate(@PathVariable(value = "id") Long id) {
        coordService.deleteCoordinate(id);
    }

    /**
     * No such coordinate.
     *
     * @param ex the ex
     */
    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public void noSuchCoordinate(NoSuchCoordinate ex) {
    }
}
