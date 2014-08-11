/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 * @param <T>
 */
public class MyCircleBuffer<T> {

    private volatile int currentRead;
    private volatile int currentWrite;
    private int size;
    private Object[] buffer;
    private Lock[] locks;
    private Condition[] readConditions;
    private Condition[] writeConditions;

    /**
     *
     * @param size
     */
    public MyCircleBuffer(int size) {
        this.size = size;
        currentRead = 0;
        currentWrite = 0;
        buffer = new Object[size];
        locks = new Lock[size];
        readConditions = new Condition[size];
        writeConditions = new Condition[size];
        for (int i = 0; i < size; i++) {
            locks[i] = new ReentrantLock();
            readConditions[i] = locks[i].newCondition();
            writeConditions[i] = locks[i].newCondition();
        }

    }

    /**
     *
     * @param item
     */
    public void put(T item) {
        if (item == null) {
            throw new RuntimeException("cannot put a null into a buffer");
        }
        int toLock = currentWrite;
        locks[toLock].lock();
        try {
            while (buffer[currentWrite] != null) {
                try {
                    writeConditions[toLock].await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyCircleBuffer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            buffer[currentWrite] = item;
            currentWrite = (currentWrite + 1) % size;
            readConditions[toLock].signalAll();
        } finally {
            locks[toLock].unlock();
        }
    }

    /**
     *
     * @return
     */
    public T get() {
        int toLock = currentRead;
        locks[toLock].lock();
        try {
            while (buffer[currentRead] == null) {
                try {
                    readConditions[toLock].await();
                } catch (InterruptedException ex) {
                    Logger.getLogger(MyCircleBuffer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            T item = (T) buffer[currentRead];
            buffer[currentRead] = null;
            currentRead = (currentRead + 1) % size;
            writeConditions[toLock].signalAll();
            return item;
        } finally {
            locks[toLock].unlock();
        }
    }
}
