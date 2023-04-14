package org.edddoubled.sudokuNinja.controller;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.edddoubled.sudokuNinja.service.SudokuSolverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("solve")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class SolveController {

    SudokuSolverService solverService;


    @GetMapping("{matrix}")
    public ResponseEntity<String> solve(@PathVariable String matrix) {
        String result = solverService.solve(matrix);
        log.info("{}\n{}", matrix, result);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
