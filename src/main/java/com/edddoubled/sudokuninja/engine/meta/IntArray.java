package com.edddoubled.sudokuninja.engine.meta;

import java.util.Arrays;

public class IntArray {
    private final int[] values;
    private final int hash;

    public IntArray(int... values) {
        this.values = values;
        Arrays.sort(values);
        this.hash = Arrays.hashCode(values);
    }

    public int[] values() {
        return values;
    }

    @Override
    public boolean equals(Object that) {
        return (that instanceof IntArray) && Arrays.equals(values, ((IntArray) that).values);
    }

    @Override
    public int hashCode() {
        return hash;
    }
}
