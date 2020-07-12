package com.example.ossystem.coordinate;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class CoordinateInputDto {
    @Min(value = 0, message = "Should be positive")
    private int position;
    @Min(value = 0, message = "Should be positive")
    private int x;
    @Min(value = 0, message = "Should be positive")
    private int y;

}
