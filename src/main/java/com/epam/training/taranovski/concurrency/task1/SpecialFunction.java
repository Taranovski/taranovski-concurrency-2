/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task1;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author Alyx
 */
public class SpecialFunction implements Function {

    /**
     *
     * @param x
     * @return
     */
    @Override
    public double calculate(double x) {
        return sin(x) * cos(x);
    }

}
