package com.example.ossystem.figure;

import com.example.ossystem.figure.exceptions.FigureNotFoundException;
import com.example.ossystem.figure.modelsAndDto.Figure;
import com.example.ossystem.figure.modelsAndDto.FigureOutPutDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<FigureOutPutDTO> getFiguresPaginatedList() {

        List<FigureOutPutDTO> list = figureService.getFiguresPaginatedList().stream()
                .map(figure -> new FigureOutPutDTO(figure.getId(), figure.getFirstName(),
                        figure.getCreatedDate().toString(), figure.getUpdatedDate().toString()))
                .collect(Collectors.toList());
        log.info("get" + list.toString());
        return list;
    }

    @GetMapping("/figures/{id}")
    public FigureOutPutDTO getFigureById(@PathVariable Integer id) {
        val maybeFigure = figureService.getFigureById(id);
        FigureOutPutDTO figureOutPutDTO = maybeFigure.
                map(figure -> new FigureOutPutDTO(figure.getId(), figure.getFirstName(),
                        figure.getCreatedDate().toString(), figure.getUpdatedDate().toString()))
                .orElseThrow(() -> new FigureNotFoundException(id));
        log.info("GET " + figureOutPutDTO.toString());
        return figureOutPutDTO;
    }

    @PostMapping("/figures")
    public ResponseEntity<?> createFigure(@RequestBody Figure figure) {
        figure.setCreatedDate(LocalDateTime.now());
        figure.setUpdatedDate(LocalDateTime.now());
        val createdFigure = figureService.createFigure(figure);
        log.info("Created " + createdFigure.toString());
        return ResponseEntity.created(uriBuilder.build(createdFigure.getId())).build();
    }

    @PutMapping("/figures/{id}")
    public ResponseEntity<?> updateFigure(@PathVariable Integer figureId,
                                          @Valid @RequestBody Figure figureDto) {
        figureService.updateFigure(figureId, figureDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/figures/{number}")
    @ResponseStatus(NO_CONTENT)
    public void deleteFigure(@PathVariable(value = "number") Integer number) {
        log.info("DELETED FIGURE with id =" + number);
        figureService.deleteFigure(number);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public void noSuchFigure(FigureNotFoundException ex) {
    }
}
