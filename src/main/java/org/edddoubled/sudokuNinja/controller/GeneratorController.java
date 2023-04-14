package org.edddoubled.sudokuNinja.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.edddoubled.sudokuNinja.service.SudokuGeneratorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("generate")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Slf4j
public class GeneratorController {
    SudokuGeneratorService generatorService;


    @GetMapping("{complexity}")
    public ResponseEntity<String> generate(@PathVariable int complexity) {
        log.info("request {}", complexity);
        if (complexity < 0 || complexity > 65) {
            return new ResponseEntity<>(generatorService.generate(true), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(generatorService.generate(complexity, true), HttpStatus.OK);
        }
    }


    @GetMapping("/tech/{complexity}")
    public ResponseEntity<String> generateTech(@PathVariable int complexity) {
        log.info("request {}", complexity);
        if (complexity < 0 || complexity > 65) {
            return new ResponseEntity<>(generatorService.generate(false), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(generatorService.generate(complexity, false), HttpStatus.OK);
        }
    }
}
