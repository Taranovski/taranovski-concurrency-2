/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alyx
 */
public class ConnectionPoolReturnDaemon extends Thread {

    private MyConnectionPool myConnectionPool;
    private Semaphore semaphore;
    private Map<Connection, Long> map;
    private long time;
    private static final int IDLE_TIMEOUT = 10000;
    private static final int CHECK_INTERVAL = 1000;

    private Connection currentConnection;

    /**
     *
     * @param myConnectionPool
     */
    public ConnectionPoolReturnDaemon(MyConnectionPool myConnectionPool) {
        this.myConnectionPool = myConnectionPool;
        semaphore = myConnectionPool.getSemaphore();
        map = myConnectionPool.getConnectionMap();
        this.setDaemon(true);
        this.start();
    }

    /**
     *
     */
    @Override
    public void run() {
        while (true) {
            time = System.currentTimeMillis();
            map = myConnectionPool.getConnectionMap();
            for (Map.Entry<Connection, Long> entry : map.entrySet()) {
                currentConnection = entry.getKey();
                if (time - entry.getValue() > IDLE_TIMEOUT) {
                    try {
                        map.remove(entry.getKey());
                        semaphore.release();
                        currentConnection.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ConnectionPoolReturnDaemon.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            try {
                Thread.sleep(CHECK_INTERVAL);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectionPoolReturnDaemon.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
