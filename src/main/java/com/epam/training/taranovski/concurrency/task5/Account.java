/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task5;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Alyx
 */
public class Account {

    private final static BigDecimal ZERO = new BigDecimal(0);
    private final int number;
    private BigDecimal currentSumm;
    private BigDecimal tempSumm;
    private Lock lock = new ReentrantLock();

    /**
     *
     * @param number
     * @param summ
     */
    public Account(int number, BigDecimal summ) {
        this.number = number;
        this.currentSumm = summ;
        this.tempSumm = summ;
        currentSumm.setScale(2, RoundingMode.DOWN);
        tempSumm.setScale(2, RoundingMode.DOWN);
    }

    /**
     *
     * @return
     */
    public int getNumber() {
        return number;
    }

    /**
     *
     * @return
     */
    public BigDecimal getSumm() {
        return currentSumm;
    }

    /**
     *
     * @param amount
     * @return
     */
    public synchronized BigDecimal withdraw(BigDecimal amount) {
        if (amount.compareTo(ZERO) < 0) {
            throw new IllegalArgumentException("can't withdraw a negative value");
        }
        if (currentSumm.compareTo(amount) <= 0) {
            return new BigDecimal(0);
        } else {
            tempSumm = currentSumm.subtract(amount);
            return amount;
        }
    }

    /**
     *
     * @param amount
     * @return
     */
    public synchronized BigDecimal deposit(BigDecimal amount) {
        if (amount.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("can't deposit a negative value");
        }
        tempSumm = currentSumm.add(amount);
        return amount;

    }

    /**
     *
     */
    public synchronized void commit() {
        currentSumm = tempSumm;
    }

    /**
     *
     */
    public synchronized void rollback() {
        tempSumm = currentSumm;
    }
}
