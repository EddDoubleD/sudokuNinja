package org.edddoubled.sudokuNinja.core.model;


import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.edddoubled.sudokuNinja.core.Utils;

import static java.util.stream.IntStream.range;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class Field {

    byte[][] field = new byte[9][9];

    public Field(String sudoku) {
        if (!sudoku.matches("\\d{81}")) {
            throw new IllegalArgumentException("sudoku");
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = (byte) (sudoku.charAt(i * 9 + j) - '0');
            }
        }
    }

    public Field(int[][] sudoku) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = (byte) sudoku[i][j];
            }
        }
    }

    public Cell cell(int i, int j) {
        return new Cell(i, j);
    }

    public Row row(int i) {
        return new Row(i);
    }

    public Row[] rows() {
        return range(0, 9).mapToObj(this::row).toArray(Row[]::new);
    }

    public Column column(int j) {
        return new Column(j);
    }

    public Column[] columns() {
        return range(0, 9).mapToObj(this::column).toArray(Column[]::new);
    }

    public Block block(int i, int j) {
        return new Block(i / 3, j / 3);
    }

    public Block[] blocks() {
        return range(0, 9).mapToObj(n -> new Block(n / 3, n % 3)).toArray(Block[]::new);
    }

    public final class Cell {

        public final int i, j;

        private Cell(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public Row row() {
            return Field.this.row(i);
        }

        public Column column() {
            return Field.this.column(j);
        }

        public Block block() {
            return Field.this.block(i, j);
        }

        public boolean empty() {
            return field[i][j] == 0;
        }

        public int index(int x) {
            return Utils.index(i, j, x);
        }

        public int value() {
            return field[i][j];
        }
    }

    public final class Row {

        public final int i;

        private Row(int i) {
            this.i = i;
        }

        public Cell cell(int j) {
            return Field.this.cell(i, j);
        }

        public Cell[] cells() {
            return range(0, 9).mapToObj(this::cell).toArray(Cell[]::new);
        }

        public boolean hasNo(int x) {
            return range(0, 9).allMatch(j -> field[i][j] != x + 1);
        }
    }

    public final class Column {

        public final int j;

        private Column(int j) {
            this.j = j;
        }

        public Cell cell(int i) {
            return Field.this.cell(i, j);
        }

        public Cell[] cells() {
            return range(0, 9).mapToObj(this::cell).toArray(Cell[]::new);
        }

        public boolean hasNo(int x) {
            return range(0, 9).allMatch(i -> field[i][j] != x + 1);
        }
    }

    public final class Block {

        public final int i, j;

        private Block(int i, int j) {
            this.i = i * 3;
            this.j = j * 3;
        }

        public Cell[] cells() {
            return range(0, 9).mapToObj(n -> cell(i + n / 3, j + n % 3)).toArray(Cell[]::new);
        }

        public boolean hasNo(int x) {
            return range(0, 9).allMatch(n -> field[i + n / 3][j + n % 3] != x + 1);
        }
    }
}
