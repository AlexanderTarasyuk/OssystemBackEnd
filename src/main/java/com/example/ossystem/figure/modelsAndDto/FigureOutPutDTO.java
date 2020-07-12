package com.example.ossystem.figure.modelsAndDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class FigureOutPutDTO {
    private int id;
    private String firstName;
    private String createdDate;
    private String updatedDate;

}
