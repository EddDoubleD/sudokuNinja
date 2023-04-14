package org.edddoubled.sudokuNinja.service;


import lombok.extern.slf4j.Slf4j;
import org.edddoubled.sudokuNinja.core.SudokuSolver;
import org.edddoubled.sudokuNinja.core.model.Field;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * combines different ways of solving sudoku
 */
@Component
@Slf4j
public class SudokuSolverService {

    /**
     * Solves sudoku using solution engine algorithms
     *
     * @param matrix incoming sudoku in one line with zeros instead of spaces
     * @return solved sudoku or error
     */
    public String solve(String matrix) {
        SudokuSolver solver = SudokuSolver.of(matrix)
                .withPrecision(1e-5)
                .withLogging(true)
                .build();

        // printing out the answer
        final StringBuilder out = new StringBuilder(171);
        long starTime = System.currentTimeMillis();
        Field result = solver.solve();
        log.error("{} ms - decision", System.currentTimeMillis() - starTime);

        Arrays.stream(result.rows()).forEach(row -> {
            Arrays.stream(row.cells()).forEach(cell -> {
                out.append(' ');
                out.append(cell.value());
            });
            out.append('\n');
        });
        return out.toString();
    }
}
