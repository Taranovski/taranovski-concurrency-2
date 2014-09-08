/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task3;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alyx
 */
public class MyConnectionPool {

    private static final int DEFAULT_SIZE = 10;
    private static final int WAITING_TIMEOUT = 1000;

    private final MyMyConnectionPool connectionPool;
    private final Semaphore semaphore;
    private final Map<Connection, Long> connectionMap;
    private final ConnectionPoolReturnDaemon daemon;

    /**
     *
     * @param size
     * @param url
     */
    public MyConnectionPool(int size, String driver, String url) {
        connectionPool = MyMyConnectionPool.getInstance(driver, url);
        connectionPool.setSize(size);
        semaphore = new Semaphore(size);
        connectionMap = new ConcurrentHashMap<>(size);
        daemon = new ConnectionPoolReturnDaemon(this);
    }

    /**
     *
     * @return
     */
    public Connection checkout() {
        try {
            if (getSemaphore().tryAcquire(WAITING_TIMEOUT, TimeUnit.MILLISECONDS)) {
                Connection con = MyConnectionInvocationHandler.createBuilderRobot(connectionPool.getConnection());
                getConnectionMap().put(con, System.currentTimeMillis());
                return con;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(MyConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new RuntimeException("no connections available");
    }

    /**
     *
     * @param c
     */
    public void checkin(Connection c) {
        try {
            getConnectionMap().remove(c);
            getSemaphore().release();
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(MyConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the semaphore
     */
    Semaphore getSemaphore() {
        return semaphore;
    }

    /**
     * @return the connectionMap
     */
    Map<Connection, Long> getConnectionMap() {
        return connectionMap;
    }

    /**
     *
     * @return
     */
    public int activeConnections() {
        return connectionPool.getCurrentPoolSize();
    }

    public int maxConnections() {
        return connectionPool.getMaxPoolSize();
    }

}
