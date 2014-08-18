/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alyx
 */
public class Runner {

    /**
     *
     */
    private Runner() {

    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MyBigInt myBigInt = new MyBigInt();
        List<Operator> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Operator(myBigInt));
        }

        for (Operator item : list) {
            item.start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Operator item : list) {
            item.stopRunning();
        }

        Set<BigInteger> set = new TreeSet<>();
        for (Operator item : list) {
            set.addAll(item.getSet());
        }

        List<BigInteger> list1 = new LinkedList<>();

        for (BigInteger number : set) {
            list1.add(number);
        }

        BigInteger int1 = null;
        BigInteger int2 = null;
        BigInteger two = new BigInteger("2");
        boolean allOk = true;
        for (int i = 1; i < list1.size(); i++) {
            int1 = list1.get(i - 1);
            int2 = list1.get(i);
            allOk = allOk & int2.divide(int1).equals(two);
        }
        System.out.println("all ok: " + allOk);

    }
}
