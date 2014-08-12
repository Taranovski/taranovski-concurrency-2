/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task5;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

/**
 *
 * @author Oleksandr_Taranovsky
 */
public class BankOperator implements Runnable {

    private final static int MAX_AMOUNT = 1000;
    private Bank bank;
    private Random random = new Random();
    private int account1;
    private int account2;
    private BigDecimal amount;

    /**
     *
     * @param bank
     */
    public BankOperator(Bank bank) {
        this.bank = bank;

    }

    /**
     *
     */
    private void generateTransaction() {
        
        
        
        amount = new BigDecimal(MAX_AMOUNT * random.nextDouble());
        System.out.println(amount);
        amount.setScale(2, RoundingMode.DOWN);
        System.out.println(amount);
    }

    /**
     *
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
