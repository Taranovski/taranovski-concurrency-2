/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task5;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alyx
 */
public class BankRun {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Bank bank = new Bank(10000);
        System.out.println("bank money: " + bank.getAllSumm() + " transaction count: " + bank.getTransactionCount());

        for (int i = 0; i < 50; i++) {
            new Thread(new BankOperator(bank)).start();
        }

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(BankRun.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("bank money: " + bank.getAllSumm() + " transaction count: " + bank.getTransactionCount());
        }

    }
}
