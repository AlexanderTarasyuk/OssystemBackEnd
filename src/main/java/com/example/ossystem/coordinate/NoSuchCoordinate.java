package com.example.ossystem.coordinate;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such a coordinate")
public class NoSuchCoordinate extends RuntimeException {
    NoSuchCoordinate(long id){
        super("No such coordinate with id = "+ id);
    }
}
