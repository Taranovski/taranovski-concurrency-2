/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author Alyx
 */
public class MyBigInt {

    private AtomicReference<BigInteger> bigInt;
    private static final BigInteger TWO = new BigInteger("2");

    public MyBigInt() {
        bigInt = new AtomicReference<>(TWO);
    }

    public BigInteger next() {
        BigInteger init = bigInt.get();
        BigInteger fin = bigInt.get().multiply(TWO);
        bigInt.compareAndSet(init, fin);
        return fin;
    }

}
