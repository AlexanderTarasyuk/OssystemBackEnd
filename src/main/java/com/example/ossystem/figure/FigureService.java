package com.example.ossystem.figure;

import com.example.ossystem.figure.exceptions.FigureNotFoundException;
import com.example.ossystem.figure.modelsAndDto.Figure;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FigureService {

    private final FiguresRepository repository;

    public List<Figure> getFiguresPaginatedList() {
        return repository.findAll();
    }

    public Optional<Figure> getFigureById(Integer figureId) {
        return repository.findById(figureId);
    }

    public Figure createFigure(Figure figure) {
        return repository.save(figure);
    }

    public Figure updateFigure(Integer figureId, Figure newFigureData) {
        val figure = repository.findById(figureId)
                .orElseThrow(() -> new FigureNotFoundException(figureId));

        figure.setFirstName(newFigureData.getFirstName());
        figure.setUpdatedDate(LocalDateTime.now());
        return repository.save(figure);
    }

    public String deleteFigure(Integer id) {
        log.info("IMPORTANT" + repository.findById(id).toString());
        var figure = repository.findById(id)
                .orElseThrow(() -> new FigureNotFoundException(id));
        repository.delete(figure);
        return "Deleted";
    }
}