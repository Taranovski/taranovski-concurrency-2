/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task1;

/**
 *
 * @author Alyx
 */
public class CalculationRunner {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Function function = new SpecialFunction();

        double number = 100;

        double mediumTime = 0;
        double mediumSumm = 0;
        int repeatNumber = 10;
        Calculation calc = null;
        double result = 0;

        for (int i = 1; i < 50; i++) {
            mediumSumm = 0;
            mediumTime = 0;
            for (int j = 1; j < repeatNumber; j++) {
                calc = new Calculation(function, number, i);
                result = calc.execute();
                mediumSumm += calc.getExecutionTime();
                //System.out.println(result);
            }
            mediumTime = mediumSumm / repeatNumber;

            System.out.println("threads: " + i + "; time: " + (long) mediumTime);
        }

    }
}
