package org.edddoubled.sudokuNinja.core;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.edddoubled.sudokuNinja.core.model.Field;
import org.edddoubled.sudokuNinja.core.model.IntArray;
import org.edddoubled.sudokuNinja.core.model.PositiveLiteral;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.edddoubled.sudokuNinja.core.Utils.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class SudokuSolver {

    final Field field;
    double precision = 1e-3;
    boolean loggingEnabled = false;

    List<int[]> positives;
    List<int[]> negatives;

    public static SudokuSolverBuilder of(String sudoku) {
        return new SudokuSolverBuilder(sudoku);
    }

    public SudokuSolver(String sudoku) {
        field = new Field(sudoku);
    }

    public void init() {
        positives = generatePositiveSat(field);
        negatives = generateNegativeSat(field);
    }

    public void setPrecision(double precision) {
        if (precision <= 0 || precision > 1) {
            throw new IllegalArgumentException("precision");
        }
        this.precision = precision;
    }

    public void enableLogging(boolean enable) {
        this.loggingEnabled = enable;
    }

    public Field solve() {
        int[][] result = new int[9][9];

        // initial values
        for (Field.Row row : field.rows()) {
            for (Field.Cell cell : row.cells()) {
                result[cell.i][cell.j] = cell.value();
            }
        }

        // values determined while system solving
        for (int index : solve0(positives, negatives)) {
            result[i(index)][j(index)] = x(index) + 1;
        }

        return new Field(result);
    }

    private int[] solve0(List<int[]> positives, List<int[]> negatives) {

        // +1-in-k-SAT into k-SAT
        Set<IntArray> set = positives.stream().map(IntArray::new).collect(toSet());

        // indexing
        final Map<Integer, Integer> mapping = new HashMap<>();
        set.forEach(array
                -> IntStream.of(array.values())
                .filter(index -> !mapping.containsKey(index)) // rely on execution order
                .forEach(index -> mapping.put(index, mapping.size()))
        );

        int N = mapping.size();
        int[] reverseMapping = new int[N];
        mapping.forEach((key, value) -> reverseMapping[value] = key);

        Set<IntArray> excluded = negatives.stream()
                .filter(a -> IntStream.of(a).allMatch(mapping::containsKey))
                .map(IntArray::new).collect(toSet());

        // preparing for calculation
        Function<Boolean, Function<IntArray, PositiveLiteral[]>> valueMapper = present -> array
                -> IntStream.of(array.values()).mapToObj(x
                -> new PositiveLiteral(mapping.get(x), present)
        ).toArray(PositiveLiteral[]::new);
        PositiveLiteral[][] disjunctions = Stream.concat(
                set.stream().map(valueMapper.apply(true)),
                excluded.stream().map(valueMapper.apply(false))
        ).toArray(PositiveLiteral[][]::new);

        int[] result = new CashKarpSolver(N, disjunctions, precision, loggingEnabled).solve();
        return IntStream.of(result).map(i -> reverseMapping[i]).toArray();
    }
}
