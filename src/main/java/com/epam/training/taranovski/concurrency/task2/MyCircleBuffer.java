/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 * @param <T>
 */
public class MyCircleBuffer<T> {

    private int current;
    private int size;
    private Object[] buffer;

    /**
     *
     * @param size
     */
    public MyCircleBuffer(int size) {
        this.size = size;
        current = 0;
        buffer = new Object[size];
    }

    /**
     *
     * @param item
     */
    public synchronized void put(T item) {
        if (item == null) {
            throw new RuntimeException("cannot put a null into a buffer");
        }
        while (buffer[current] != null) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyCircleBuffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        buffer[current] = item;
        current = (current + 1) % size;
        notifyAll();
    }

    /**
     *
     * @return
     */
    public synchronized T get() {
        while (buffer[current] == null) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyCircleBuffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        T item = (T) buffer[current];
        buffer[current] = null;
        current = (current + 1) % size;
        notifyAll();
        return item;
    }

}
