package com.example.ossystem.figure;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class FigureController {

    private final FigureService figureService;
    private final UriBuilder uriBuilder;

    public FigureController(FigureService figureService,
                            @Value("${figure.host-name:localhost}") String hostName) {
        this.figureService = figureService;
        uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostName)
                .path("/figures/{id}");
    }

    @GetMapping("/figures")
    public Page<Figure> getFiguresPaginatedList(Pageable pageable) {

        return figureService.getFiguresPaginatedList(pageable);
    }

    @GetMapping("/figures/{id}")
    public Figure getFigureById(@PathVariable Integer figureId) {
        val maybeFigure = figureService.getFigureById(figureId);
        return maybeFigure.orElseThrow(() -> new FigureNotFoundException(figureId));
    }

    @PostMapping("/figures")
    public ResponseEntity<?> createFigure(@RequestBody Figure figureDto) {
//        val figure = new Figure(figureDto.getName(), figureDto.getCreatedAt(), figureDto.getUpdatedAt());
        val createdFigure = figureService.createFigure(figureDto);
        return ResponseEntity.created(uriBuilder.build(createdFigure.getId())).build();
    }

    @PutMapping("/figures/{id}")
    public ResponseEntity<?> updateFigure(@PathVariable Integer figureId,
                                          @Valid @RequestBody FigureInputDTO figureDto) {
        val figure = new Figure(figureDto.getName(), figureDto.getCreatedAt(), figureDto.getUpdatedAt());
        figureService.updateFigure(figureId, figure);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/figures/{number}")
    @ResponseStatus(NO_CONTENT)
    public void deleteFigure(@PathVariable(value = "number") Integer number) {
        figureService.deleteFigure(number);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public void noSuchFigure(FigureNotFoundException ex) {
    }
}
