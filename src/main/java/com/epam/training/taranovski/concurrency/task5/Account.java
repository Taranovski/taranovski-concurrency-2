/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epam.training.taranovski.concurrency.task5;

import java.math.BigDecimal;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Alyx
 */
public class Account {
    private final int number;
    private BigDecimal summ;
    private Lock lock = new ReentrantLock();

    public Account(int number, BigDecimal summ) {
        this.number = number;
        this.summ = summ;
    }

    public int getNumber() {
        return number;
    }

    public BigDecimal getSumm() {
        return summ;
    }
    
    public void withdraw(BigDecimal amount) {
        if (summ.compareTo(amount) < 0) {
            return;
        } else {
            
        }
        
    }
    
    public void deposit(BigDecimal amount) {
        
    }
}
