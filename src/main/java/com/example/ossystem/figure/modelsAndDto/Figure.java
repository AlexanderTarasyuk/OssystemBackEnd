package com.example.ossystem.figure.modelsAndDto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The type Figure.
 */
@Data
@Entity
@Table(name = "figures")
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;
    private String firstName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    /**
     * Instantiates a new Figure.
     */
    public Figure() {
    }

    /**
     * Instantiates a new Figure.
     *
     * @param version   the version
     * @param firstName the first name
     * @param lastName  the last name
     * @param emaiId    the emai id
     * @param active    the active
     */
    public Figure(Integer version, String firstName, String lastName, String emaiId, boolean active) {
        this.version = version;
        this.firstName = firstName;
    }

    /**
     * Instantiates a new Figure.
     *
     * @param firstName the first name
     */
    public Figure(String firstName) {
        this.firstName = firstName;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }

    /**
     * Instantiates a new Figure.
     *
     * @param i      the
     * @param circle the circle
     */
    public Figure(int i, String circle) {
        id = i;
        firstName = circle;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }
}