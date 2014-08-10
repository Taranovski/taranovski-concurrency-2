/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task2;

/**
 *
 * @author user
 */
public class Runner {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        MyCircleBuffer<Integer> buffer = new MyCircleBuffer<>(10);
        Consumer<Integer> consumer = new Consumer(buffer);
        Producer<Integer> producer = new Producer(buffer, new MyItemGenerator());

        new Thread(consumer).start();
        new Thread(producer).start();
    }

}
