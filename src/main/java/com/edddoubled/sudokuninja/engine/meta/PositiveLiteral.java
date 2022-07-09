package com.edddoubled.sudokuninja.engine.meta;

import lombok.Data;

@Data
public class PositiveLiteral {

    public final int ijx;
    public final boolean present;

    public PositiveLiteral(int a, boolean present) {
        this.ijx = a;
        this.present = present;
    }
}
