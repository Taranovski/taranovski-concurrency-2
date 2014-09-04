/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Alyx
 */
public class Operator extends Thread {

    private MyBigInt myBigInt;
    private volatile boolean running = true;
    private List<BigInteger> list;

    /**
     *
     * @param myBigInt
     */
    public Operator(MyBigInt myBigInt) {
        this.myBigInt = myBigInt;
        list = new LinkedList<>();
    }

    /**
     *
     */
    @Override
    public void run() {
        BigInteger bigInt = null;
        while (running) {
            bigInt = myBigInt.next();
            list.add(bigInt);
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
    public List<BigInteger> getList() {
        return list;
    }

}
