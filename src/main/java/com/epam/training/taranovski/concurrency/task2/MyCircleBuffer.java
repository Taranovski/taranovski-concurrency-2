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
    private final Lock lock;
    private final Condition readCondition;
    private final Condition writeCondition;

    /**
     *
     * @param size
     */
    public MyCircleBuffer(int size) {
        this.size = size;
        currentRead = 0;
        currentWrite = 0;
        buffer = new Object[size];
        lock = new ReentrantLock();
        readCondition = lock.newCondition();
        writeCondition = lock.newCondition();
    }

    /**
     *
     * @param item
     */
    public void put(T item) {
        if (item == null) {
            throw new RuntimeException("cannot put a null into a buffer");
        }
        while (true) {
            if (lock.tryLock()) {
                try {
                    while (buffer[currentWrite] != null) {
                        try {
                            
                            
                            writeCondition.await();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyCircleBuffer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    buffer[currentWrite] = item;
                    currentWrite = (currentWrite + 1) % size;
                    readCondition.signalAll();
                    return;
                } finally {
                    lock.unlock();
                }
            }
        }

    }

    /**
     *
     * @return
     */
    public T get() {
        while (true) {
            if (lock.tryLock()) {
                try {
                    while (buffer[currentRead] == null) {
                        try {
                            readCondition.await();
                        } catch (InterruptedException ex) {
                            Logger.getLogger(MyCircleBuffer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    T item = (T) buffer[currentRead];
                    buffer[currentRead] = null;
                    currentRead = (currentRead + 1) % size;
                    writeCondition.signalAll();
                    return item;
                } finally {
                    lock.unlock();
                }
            }
        }

    }
}
