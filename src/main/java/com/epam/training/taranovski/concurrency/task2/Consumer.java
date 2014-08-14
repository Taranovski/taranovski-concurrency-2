/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task2;

/**
 *
 * @author user
 * @param <T>
 */
public class Consumer<T> implements Runnable {

    private final MyCircleBuffer<T> buffer;
    T item;

    /**
     *
     * @param buffer
     */
    public Consumer(MyCircleBuffer<T> buffer) {
        this.buffer = buffer;
    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            //System.out.println("consumer " + this + "trying to get...");
            item = buffer.get();
            //System.out.println("got: " + item);
        }
    }

}
