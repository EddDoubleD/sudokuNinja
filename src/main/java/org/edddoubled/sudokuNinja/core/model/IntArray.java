package org.edddoubled.sudokuNinja.core.model;

import java.util.Arrays;

public record IntArray(int... values) {
    public IntArray {
        Arrays.sort(values);
    }


    @Override
    public boolean equals(Object that) {
        return (that instanceof IntArray) && Arrays.equals(values, ((IntArray) that).values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
