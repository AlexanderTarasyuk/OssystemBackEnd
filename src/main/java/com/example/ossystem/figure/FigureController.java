package com.example.ossystem.figure;

import com.example.ossystem.figure.exceptions.FigureNotFoundException;
import com.example.ossystem.figure.modelsAndDto.Figure;
import com.example.ossystem.figure.modelsAndDto.FigureOutPutDTO;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.hibernate.StaleObjectStateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * The type Figure controller.
 */
@RestController
@CrossOrigin(origins = "${general.port}")
@Slf4j
public class FigureController {

    private final FigureService figureService;
    private final UriBuilder uriBuilder;

    /**
     * Instantiates a new Figure controller.
     *
     * @param figureService the figure service
     * @param hostName      the host name
     */
    public FigureController(FigureService figureService,
                            @Value("${figure.host-name:localhost}") String hostName) {
        this.figureService = figureService;
        uriBuilder = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(hostName)
                .path("/figures/{id}");
    }

    /**
     * Gets figures paginated list.
     *
     * @return the figures paginated list
     */
    @GetMapping("/figures")
    public List<FigureOutPutDTO> getFiguresPaginatedList() {
//this line just to show inderstanding of pagination in spring boot
//        final Pageable pageable = PageRequest.of(6, 5, true);
        List<FigureOutPutDTO> list = figureService.getFiguresPaginatedList().stream()
                .map(figure -> new FigureOutPutDTO(figure.getId(), figure.getFirstName(),
                        figure.getCreatedDate().toString(), figure.getUpdatedDate().toString()))
                .collect(Collectors.toList());
        log.info("get" + list.toString());
        return list;
    }

    /**
     * Gets figure by id.
     *
     * @param id the id
     * @return the figure by id
     */
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

    /**
     * Create figure response entity.
     *
     * @param figure the figure
     * @return the response entity
     */
    @PostMapping("/figures")
    public ResponseEntity<?> createFigure(@RequestBody Figure figure) {
        figure.setCreatedDate(LocalDateTime.now());
        figure.setUpdatedDate(LocalDateTime.now());
        val createdFigure = figureService.createFigure(figure);
//        log.info("Created " + createdFigure.toString());
        return ResponseEntity.created(uriBuilder.build(createdFigure.getId())).build();
    }

    /**
     * Update figure response entity.
     *
     * @param id        the id
     * @param figureDto the figure dto
     * @return the response entity
     */
    @Retryable(StaleObjectStateException.class)

    @PutMapping("/figures/{id}")
    public ResponseEntity<?> updateFigure(@PathVariable Integer id,
                                          @Valid @RequestBody Figure figureDto) {
        figureService.updateFigure(id, figureDto);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete figure.
     *
     * @param number the number
     */
    @DeleteMapping("/figures/{number}")
    @ResponseStatus(NO_CONTENT)
    public void deleteFigure(@PathVariable(value = "number") Integer number) {
        log.info("DELETED FIGURE with id =" + number);
        figureService.deleteFigure(number);
    }

    /**
     * No such figure.
     *
     * @param ex the ex
     */
    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public void noSuchFigure(FigureNotFoundException ex) {
    }
}
