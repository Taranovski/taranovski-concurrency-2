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
    private boolean running = true;

    /**
     *
     * @param myConnectionPool
     */
    public ConnectionPoolReturnDaemon(MyConnectionPool myConnectionPool) {
        this.myConnectionPool = myConnectionPool;
        semaphore = myConnectionPool.getSemaphore();
        map = myConnectionPool.getConnectionMap();
    }

    @Override
    public void run() {
        Connection currentConnection;
        while (running) {
            time = System.currentTimeMillis();
            map = myConnectionPool.getConnectionMap();
            
            //System.out.println("111");
            for (Map.Entry<Connection, Long> entry : map.entrySet()) {
                currentConnection = entry.getKey();
                if (time - entry.getValue() > IDLE_TIMEOUT) {
                    try {
                        map.remove(entry.getKey());
                        semaphore.release();
                        currentConnection.close();
                        //System.out.println("closed");
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

    /**
     *
     */
    public void stopDaemon() {
        running = false;
    }
}
