package org.edddoubled.sudokuNinja.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * End-point for health check
 */
@RestController
@RequestMapping("health")
@Slf4j
public class HealthCheckController {

    @GetMapping
    public ResponseEntity<?> meAlive() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
