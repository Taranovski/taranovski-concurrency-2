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

    /**
     *
     * @param accountNumber1
     * @param accountNumber2
     * @param amount
     */
    public void makeTransaction(int accountNumber1, int accountNumber2, BigDecimal amount) {
        if (accountNumber1 == accountNumber2) {
            return;
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException();
        }

        boolean success = false;

        int first = accountNumber1;
        int second = accountNumber2;

        if (accountNumber1 > accountNumber2) {
            first = accountNumber2;
            second = accountNumber1;
        }

        while (!success) {
            if (accounts.get(first).getLock().tryLock()) {
                try {
                    if (accounts.get(second).getLock().tryLock()) {
                        try {
                            if (checkAmount(accountNumber1, amount)) {
                                accounts.get(accountNumber1).withdraw(amount);
                                accounts.get(accountNumber2).deposit(amount);
                                success = true;
                            }
                        } finally {
                            accounts.get(second).getLock().unlock();
                        }
                    }
                } finally {
                    accounts.get(first).getLock().unlock();
                }
            }
        }
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
