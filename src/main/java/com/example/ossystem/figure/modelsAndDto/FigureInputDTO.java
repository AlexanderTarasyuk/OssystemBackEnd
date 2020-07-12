package com.example.ossystem.figure.modelsAndDto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class FigureInputDTO {


    private int id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
