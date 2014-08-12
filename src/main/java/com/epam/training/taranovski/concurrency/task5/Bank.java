/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task5;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Alyx
 */
public class Bank {

    List<Account> accounts;

    public void makeTransaction(int accountNumber1, int accountNumber2, BigDecimal amount) {

    }

    /**
     *
     * @return
     */
    public boolean checkAllPositive() {
        boolean allPositive = true;
        for (Account account : accounts) {
            allPositive = allPositive & (account.getSumm().signum() >= 0);
        }
        return allPositive;
    }

    /**
     *
     * @param accountNumber
     * @param amount
     * @return
     */
    private boolean checkAmount(int accountNumber, BigDecimal amount) {
        return accounts.get(accountNumber).getSumm().compareTo(amount) >= 0;
    }

    /**
     *
     * @return
     */
    public BigDecimal getAllSumm() {
        BigDecimal summ = new BigDecimal(0);
        for (Account account : accounts) {
            summ = summ.add(account.getSumm());
        }
        return summ;
    }
}
