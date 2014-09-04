/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alyx
 */
public class ConnectionPoolRunner {

    /**
     *
     * @param args
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        MyConnectionPool pool = new MyConnectionPool("jdbc:h2:~/test", "sa", "");
        
        System.out.println("pool active: " + pool.activeConnections());
        System.out.println("pool max: " + pool.maxConnections());

        Connection con1 = pool.checkout();

        System.out.println("pool active: " + pool.activeConnections());
        System.out.println("pool max: " + pool.maxConnections());

        try {
            con1.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionPoolRunner.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("pool active: " + pool.activeConnections());
        System.out.println("pool max: " + pool.maxConnections());

        try {
            for (int i = 0; i < 12; i++) {
                Connection con2 = pool.checkout();
                System.out.println("pool active: " + pool.activeConnections());
                System.out.println("pool max: " + pool.maxConnections());
            }
        } catch (RuntimeException e) {
            System.out.println("caught " + e);
        }

        for (int i = 0; i < 20; i++) {
            System.out.println("pool active: " + pool.activeConnections());
            System.out.println("pool max: " + pool.maxConnections());
            System.out.println(i);
            Thread.sleep(1000);
        }

        System.out.println("pool active: " + pool.activeConnections());
        System.out.println("pool max: " + pool.maxConnections());
        Connection con3 = pool.checkout();

        Thread.sleep(1000);
        System.out.println("pool active: " + pool.activeConnections());
        System.out.println("pool max: " + pool.maxConnections());
        pool.checkin(con3);

        System.out.println("pool active: " + pool.activeConnections());
        System.out.println("pool max: " + pool.maxConnections());

    }

}
