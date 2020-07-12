package com.example.ossystem.figure;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "figures")
public class Figure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Version
    private Integer version;

    private String name;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Figure(String name, Timestamp createdAt, Timestamp updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Figure(String name) {
        Timestamp creationTime = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.createdAt = creationTime;
        this.updatedAt = creationTime;
    }

    public Figure() {
    }


}