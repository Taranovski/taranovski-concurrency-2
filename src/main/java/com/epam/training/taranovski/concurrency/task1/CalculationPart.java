/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task1;

import java.util.concurrent.Callable;

/**
 *
 * @author Alyx
 */
public class CalculationPart implements Callable<Double> {

    Function function;

    double start;
    double end;
    double step;

    /**
     *
     * @param function
     * @param start
     * @param end
     * @param step
     */
    public CalculationPart(Function function, double start, double end, double step) {
        this.function = function;
        this.start = start;
        this.end = end;
        this.step = step;
    }

    @Override
    public Double call() throws Exception {
        double result = 0;
        for (double current = start; current < end; current += step) {
            result = result + function.calculate(current) + function.calculate(-current);
        }
        return result;
    }

}
