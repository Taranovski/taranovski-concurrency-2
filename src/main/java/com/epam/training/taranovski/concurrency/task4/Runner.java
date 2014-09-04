/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
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
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (Operator item : list) {
            item.stopRunning();
        }
        
        for (Operator item : list) {
            try {
                item.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Runner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<BigInteger> list1 = new LinkedList<>();
        for (Operator item : list) {
            list1.addAll(item.getList());
        }

        Collections.sort(list1);

        BigInteger int1 = null;
        BigInteger int2 = null;
        BigInteger two = new BigInteger("2");
//        System.out.println(two.multiply(two).equals(two.multiply(two)));
//        System.out.println(two.multiply(two).equals(two));
//        System.out.println(new BigInteger("256").divide(two).equals(new BigInteger("128")));
        System.out.println("size: " + list1.size());
        boolean allOk = true;
        for (int i = 1; i < list1.size(); i++) {
            int1 = list1.get(i - 1);
            int2 = list1.get(i);
            allOk = allOk & int2.divide(int1).equals(two);
            if (!int2.divide(int1).equals(two)) {
                System.out.println(i);
            }
        }
        System.out.println("all ok: " + allOk);

    }
}
