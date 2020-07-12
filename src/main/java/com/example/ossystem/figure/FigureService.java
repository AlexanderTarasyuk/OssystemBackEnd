package com.example.ossystem.figure;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FigureService {

    private final FiguresRepository repository;

    public Page<Figure> getFiguresPaginatedList(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Figure> getFigureById(Integer figureId) {
        return repository.findById(figureId);
    }

    public Figure createFigure(Figure figure){
        return repository.save(figure);
    }

    public Figure updateFigure(Integer figureId, Figure newFigureData){
        val figure = repository.findById(figureId)
                .orElseThrow(() -> new FigureNotFoundException(figureId));

        figure.setName(newFigureData.getName());
        figure.setUpdatedAt( new Timestamp(System.currentTimeMillis()) );
        return repository.save(figure);
    }

    public String deleteFigure(Integer id){
        log.info("IMPORTANT" +repository.findById(id).toString());
        var figure = repository.findById(id)
                .orElseThrow(() -> new FigureNotFoundException(id));
        repository.delete(figure);
        return "Deleted";
    }

    public void deleteAllFigure(){
//        val figure = repository.findById(figureId)
//                .orElseThrow(() -> new FigureNotFoundException());
        repository.deleteAll();
    }
}