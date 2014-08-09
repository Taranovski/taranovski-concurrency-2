/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alyx
 */
public class Calculation {

    private static final double DEFAULT_STEP = 0.0001;
    private static double step = DEFAULT_STEP;

    private static final int DEFAULT_THREAD_NUMBER = 1;

    private int threadCount = DEFAULT_THREAD_NUMBER;
    private long executionTime = 0;

    private double start;
    private double end;
    private Function function;
    private double result = 0;
    private double tempStep;

    /**
     *
     * @param function
     * @param number
     * @param threadCount
     */
    public Calculation(Function function, double number, int threadCount) {
        if (threadCount > 1) {
            this.threadCount = threadCount;
        }
        if (number > 0) {
            this.start = -number;
            this.end = number;
        } else {
            this.start = number;
            this.end = -number;
        }

        this.function = function;
        this.tempStep = step * threadCount;
    }

    /**
     *
     * @return
     */
    public double execute() {

        List<Callable<Double>> callableList = new ArrayList<>();
        List<Future<Double>> futureList = null;

        ExecutorService service = Executors.newFixedThreadPool(threadCount);

        double temp = 0;
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < threadCount; i++) {
            callableList.add(new CalculationPart(function, start + temp, end, tempStep));
            temp += step;
        }

        try {
            futureList = service.invokeAll(callableList);
        } catch (InterruptedException ex) {
            Logger.getLogger(Calculation.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            for (int i = 0; i < threadCount; i++) {
                result += futureList.get(i).get();
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Calculation.class.getName()).log(Level.SEVERE, null, ex);
        }
        service.shutdown();

        executionTime = System.currentTimeMillis() - startTime;
        return result;
    }

    /**
     * @return the threadCount
     */
    public int getThreadCount() {
        return threadCount;
    }

    /**
     * @return the executionTime
     */
    public long getExecutionTime() {
        return executionTime;
    }

    /**
     * @return the start
     */
    public double getStart() {
        return start;
    }

    /**
     * @return the end
     */
    public double getEnd() {
        return end;
    }

    /**
     * @return the function
     */
    public Function getFunction() {
        return function;
    }

    /**
     * @return the step
     */
    public static double getStep() {
        return step;
    }

    /**
     * @param aStep the step to set
     */
    public static void setStep(double aStep) {
        step = aStep;
    }

}
