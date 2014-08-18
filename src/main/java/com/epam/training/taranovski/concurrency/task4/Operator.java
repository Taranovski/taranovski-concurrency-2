/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

import java.math.BigInteger;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Alyx
 */
public class Operator extends Thread {

    private MyBigInt myBigInt;
    private boolean running = true;
    private Set<BigInteger> treeSet;

    /**
     *
     * @param myBigInt
     */
    public Operator(MyBigInt myBigInt) {
        this.myBigInt = myBigInt;
        treeSet = new TreeSet<>();
    }

    /**
     *
     */
    @Override
    public void run() {
        BigInteger bigInt = null;
        while (running) {
            bigInt = myBigInt.next();
            treeSet.add(bigInt);
            //System.out.println(bigInt);
        }
    }

    /**
     *
     */
    public void stopRunning() {
        running = false;
    }

    /**
     *
     * @return
     */
    public Set<BigInteger> getSet() {
        return treeSet;
    }

}
