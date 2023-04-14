package org.edddoubled.sudokuNinja.service;


import lombok.extern.slf4j.Slf4j;
import org.edddoubled.sudokuNinja.core.SudokuGenerator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SudokuGeneratorService {

    public String generate(boolean beautiful) {
        SudokuGenerator generator = new SudokuGenerator();
        return generator.fillValues().printSudoku(beautiful);

    }

    public String generate(int missingNumbers, boolean beautiful) {
        SudokuGenerator generator = new SudokuGenerator(missingNumbers);
        return generator.fillValues().printSudoku(beautiful);
    }
}
