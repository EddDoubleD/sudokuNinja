package com.edddoubled.sudokuninja.engine.meta;

public final class Constant {

    public static final double[][] BUTCHER_TABLEUAU = {
            { 0,    0,     0,    0,     0    },
            { 1,    0,     0,    0,     0    },
            { 3,    9,     0,    0,     0    },
            { 3,   -9,     12,   0,     0    },
            {-11,   135,  -140,  70,    0    },
            { 3262, 37800, 4600, 44275, 6831 },
    };

    private static final double[] DIVIDERS = { 1, 5, 40, 10, 54, 110592 };
    private static final double[] B5 = { 37d/378,     0, 250d/621,     125d/594,     0,          512d/1771 };
    private static final double[] B4 = { 2825d/27648, 0, 18575d/48384, 13525d/55296, 277d/14336, 1d/4      };



}
