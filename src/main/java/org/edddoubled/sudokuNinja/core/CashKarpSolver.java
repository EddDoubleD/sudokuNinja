package org.edddoubled.sudokuNinja.core;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.edddoubled.sudokuNinja.core.model.Coefficient;
import org.edddoubled.sudokuNinja.core.model.PositiveLiteral;

import java.util.Optional;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.System.arraycopy;
import static java.util.stream.IntStream.range;
import static org.edddoubled.sudokuNinja.core.Utils.*;

/**
 * J.R.Cash and A.H.Karp method for solving ordinary differential equations
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CashKarpSolver {
    int N, M, L; // L is N + M
    double precision;
    boolean loggingEnabled;
    Coefficient[][] d1; // sparse matrix of clauses
    Coefficient[][] d2; // transposed sparse matrix of clauses
    long[] km; // normalization for Km and Kmi methods
    double[] y;

    public CashKarpSolver(int N, PositiveLiteral[][] disjunctions, double precision, boolean loggingEnabled) {
        this.N = N;
        this.M = disjunctions.length;
        this.L = this.N + this.M;
        this.precision = precision;
        this.loggingEnabled = loggingEnabled;
        this.y = new double[L];
        this.d1 = Stream.of(disjunctions).map(d
                -> Stream.of(d).map(v -> new Coefficient(v.ijx(), v.present() ? 1 : -1)).toArray(Coefficient[]::new)
        ).toArray(Coefficient[][]::new);
        this.d2 = range(0, N).mapToObj(i
                        -> range(0, M).mapToObj(m
                        -> Stream.of(d1[m])
                        .filter(pair -> pair.index() == i)
                        .findFirst()
                        .map(pair -> new Coefficient(m, pair.c()))
                ).filter(Optional::isPresent)
                        .map(Optional::get).toArray(Coefficient[]::new)
        ).toArray(Coefficient[][]::new);
        this.km = Stream.of(disjunctions).mapToLong(d -> 1L << d.length).toArray();
    }

    private static double randomDouble(Random random) {
        return random.nextDouble() * 0.9999998 + 0.0000001; // avoid 0 and 1
    }

    public int[] solve() {
        double dt = 0.03125, t = 0, dmax = 1;

        Random random = new Random();
        range(0, N).forEach(i -> y[i] = 2 * randomDouble(random) - 1);
        range(N, L).forEach(i -> y[i] = randomDouble(random));
        Stream.of(d1).filter(l -> l.length == 1).forEach(l -> y[l[0].index()] = l[0].c()); // cheap optimization, works sometimes

        double[] _y = new double[L];
        double[][] k = new double[BUTCHER_TABLEUAU.length][L];

        arraycopy(y, 0, _y, 0, L);
        int outCount = 0; // logging stuff

        while (dmax > 0.1) {
            for (int b = 0; b < BUTCHER_TABLEUAU.length; b++) {
                {
                    double[] butcherTableau_b = BUTCHER_TABLEUAU[b];
                    double divider = DIVIDERS[b];
                    for (int l = 0; l < L; l++) {
                        double y_l = 0;
                        for (int j = 0; j < b; j++) y_l += butcherTableau_b[j] * k[j][l] / divider;
                        y[l] = _y[l] + dt * y_l;
                    }
                }

                double[] k_b = k[b];
                for (int i = 0; i < N; i++) k_b[i] = ds(i);
                for (int m = 0; m < M; m++) k_b[N + m] = da(m);
            }

            double tau = 0;
            for (int l = 0; l < L; l++) {
                double err = 0, y_l = 0;
                for (int j = 0; j < B5.length; j++) {
                    double k_jl = k[j][l];
                    err += B4[j] * k_jl;
                    y_l += B5[j] * k_jl;
                }
                err -= y_l;
                if (err < 0) err = -err;
                err = _y[l] == 0d ? 0 : err / _y[l];
                if (err > tau) tau = err;
                y[l] = _y[l] + dt * y_l;
            }
            tau *= dt;
            dt *= 0.9 * min(max(precision / tau, 0.3), 2); // right from wikipedia!

            if (/*dt > 1e-6 && */tau > precision) {
                outCount = (outCount + 1) % 1000;
                if (outCount == 0) {
                    if (loggingEnabled) {
                        //noinspection OptionalGetWithoutIsPresent
                        double max = DoubleStream.of(_y).skip(N).max().getAsDouble();
                        log.debug(t + "\t" + dmax + "\t" + max + "\n");
                    }
                }
                continue;
            }

            t += dt;

            dmax = 0;
            for (int m = 0; m < M; m++) {
                double d = Km(m);
                if (d > dmax) dmax = d;
            }

            arraycopy(y, 0, _y, 0, L);
        }
        if (loggingEnabled) {
            log.debug("{}\n", t);
        }
        return range(0, N).filter(i -> y[i] > 0).toArray();
    }

    private double Km(int m) {
        double result = 1;
        for (Coefficient pair : d1[m]) {
            result *= (1 - y[pair.index()] * pair.c());
        }
        return result / km[m];
    }

    private double Kmi(int m, int i) {
        double result = 1;
        for (Coefficient pair : d1[m]) {
            if (pair.index() == i) {
                continue;
            }
            result *= (1 - y[pair.index()] * pair.c());
        }
        return result / km[m];
    }

    // ds/dt
    private double ds(int i) {
        double result = 0;
        for (Coefficient pair : d2[i]) {
            int m = pair.index();
            double kmi = Kmi(m, i);
            result += y[N + m] * pair.c() * kmi * kmi * (1 - y[i] * pair.c());
        }
        return result + result;
    }

    // da/dt
    private double da(int m) {
        return y[N + m] * Km(m);
    }
}
