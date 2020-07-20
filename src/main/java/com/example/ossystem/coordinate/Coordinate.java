package com.example.ossystem.coordinate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * The type Coordinate.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coordinates")
public class Coordinate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Integer version;
    private int position;

    @Min(value = 0, message = "Should be positive")
    private int x;
    @Min(value = 0, message = "Should be positive")
    private int y;

    /**
     * Instantiates a new Coordinate.
     *
     * @param position the position
     * @param x        the x
     * @param y        the y
     */
    public Coordinate(int position, @Min(value = 0, message = "Should be positive") int x, @Min(value = 0, message = "Should be positive") int y) {
        this.position = position;
        this.x = x;
        this.y = y;
    }
}
