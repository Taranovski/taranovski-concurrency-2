/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task4;

/**
 *
 * @author Alyx
 */
public class Operator extends Thread {

    private MyBigInt myBigInt;
    private boolean running = true;

    /**
     *
     * @param myBigInt
     */
    public Operator(MyBigInt myBigInt) {
        this.myBigInt = myBigInt;
    }

    /**
     *
     */
    @Override
    public void run() {
        while (running) {
            System.out.println(myBigInt.next());
        }
    }

    /**
     *
     */
    public void stopRunning() {
        running = false;
    }

}
