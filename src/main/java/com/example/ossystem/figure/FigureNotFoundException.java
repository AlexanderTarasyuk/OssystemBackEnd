package com.example.ossystem.figure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such a figure")
public class FigureNotFoundException extends RuntimeException {
    FigureNotFoundException(int id){
        super("No such Figure with id = " +id);
    }
}
