package com.example.ossystem.figure.modelsAndDto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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


//    @Column(name = "created_at")
//    private Timestamp createdAt;
//
//    @Column(name = "updated_at")
//    private Timestamp updatedAt;

//    public Figure(String name, Timestamp createdAt, Timestamp updatedAt) {
//        this.name = name;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
//
//    public Figure(String name) {
//        Timestamp creationTime = new Timestamp(System.currentTimeMillis());
//        this.name = name;
//        this.createdAt = creationTime;
//        this.updatedAt = creationTime;
//    }

    public Figure() {
    }

    public Figure(Integer version, String firstName, String lastName, String emaiId, boolean active) {
        this.version = version;
        this.firstName = firstName;
    }

    public Figure(String firstName) {
        this.firstName = firstName;
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
    }



}