package com.edddoubled.sudokuninja.controller;

import com.edddoubled.sudokuninja.SudokuSolverService;
import com.edddoubled.sudokuninja.model.SolutionMetadata;
import com.edddoubled.sudokuninja.repository.SolutionMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("solve")
@Slf4j
public class SolveController {

    @Autowired
    private SudokuSolverService solverService;
    @Autowired
    private SolutionMetadataRepository repository;


    @GetMapping("{matrix}")
    public String solve(@PathVariable String matrix) {
        String hash = String.valueOf(matrix.hashCode());
        Optional<SolutionMetadata> metadata = repository.findById(hash);
        if (metadata.isPresent()) {
            return metadata.get().getSolution();
        }

        String result = solverService.solve(matrix);
        repository.save(new SolutionMetadata(hash, result, null));

        return result;
    }
}
