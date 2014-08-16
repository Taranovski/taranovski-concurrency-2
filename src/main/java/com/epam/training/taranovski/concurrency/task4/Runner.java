/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.openmbean.OpenDataException;

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

        for (int i = 0; i < 20; i++) {
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

    }
}
