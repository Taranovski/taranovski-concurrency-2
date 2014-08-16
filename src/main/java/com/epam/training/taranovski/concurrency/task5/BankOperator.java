/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task5;

import java.math.BigDecimal;
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
    private int account1Number;
    private int account2Number;
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
        amount = new BigDecimal(0.01 + (MAX_AMOUNT - 0.01) * random.nextDouble()).setScale(2, RoundingMode.DOWN);
        //System.out.println(amount);
        account1Number = random.nextInt(bank.getBankSize());
        account2Number = random.nextInt(bank.getBankSize());
        bank.makeTransaction(account1Number, account2Number, amount);
    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            generateTransaction();
        }
    }

}
