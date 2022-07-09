package com.edddoubled.sudokuninja.engine.meta;

import lombok.Data;

@Data
public class Coefficient {
    final int index, c;

    public Coefficient(int index, int c) {
        this.index = index;
        this.c = c;
    }
}
