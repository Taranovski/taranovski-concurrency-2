/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 * a connection pool for mysql database
 *
 * @author Alyx
 */
public class MyMyConnectionPool {

    private MyMyConnectionPool instance;

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_DELAY = 50;

    private Deque<Connection> connections = new LinkedList<>();

    private int maxPoolSize = 0;
    private int delay = 0;

    private int currentPoolSize = 0;
    private String driverName;
    private String url;

    /**
     * private constructor for singleton pattern
     *
     * @param size wanted pool size
     * @param delay wanted delay for connection retries
     */
    private MyMyConnectionPool(int size, int delay) {
        this.maxPoolSize = size;
        this.delay = delay;
    }

    /**
     * set a size of pool
     *
     * @param size wanted size
     */
    public void setSize(int size) {
        if (size > 0) {
            // if size > current pool size - just setting new value
            if (size < currentPoolSize) {
                // if size < current size of deque, deleting and closing connections
                // else just setting new value
                if (connections.size() > size) {
                    while (connections.size() > size) {
                        try {
                            currentPoolSize--;
                            connections.removeLast().close();
                        } catch (SQLException ex) {
                            Logger.getLogger(MyMyConnectionPool.class.getName()).error(ex);
                        }
                    }
                } else {
                    currentPoolSize = size;
                }
            }
            this.maxPoolSize = size;
        }
    }

    /**
     * get instance method
     *
     * @param driver
     * @param url
     * @return an instance of connection pool
     */
    public static MyMyConnectionPool getInstance(String driver, String url) {
        MyMyConnectionPool instance = new MyMyConnectionPool(DEFAULT_SIZE, DEFAULT_DELAY);
        instance.driverName = driver;
        instance.url = url;
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyMyConnectionPool.class.getName()).error(ex);
        }

        return instance;
    }

    /**
     * get connection method
     *
     * @return connection
     */
    public Connection getConnection() {

        if (!connections.isEmpty()) {
            Logger.getLogger(MyMyConnectionPool.class.getName()).info("connection from deque, deque size: " + connections.size());
            return connections.pollFirst();
        } else if (currentPoolSize < maxPoolSize) {
            try {
                currentPoolSize++;
                Logger.getLogger(MyMyConnectionPool.class.getName()).info("new connection, deque size: " + connections.size());

                return DriverManager.getConnection(url);
            } catch (SQLException ex) {
                Logger.getLogger(MyMyConnectionPool.class.getName()).error(ex);
            }
        }

        return null;
    }

    /**
     * puts a used connection to deque
     *
     * @param connection connection to put
     */
    public void free(Connection connection) {
        if (connections.size() < maxPoolSize) {
            Logger.getLogger(MyMyConnectionPool.class.getName()).info("connection put to deque, deque size: " + connections.size());

            connections.addFirst(connection);
        } else {
            Logger.getLogger(MyMyConnectionPool.class.getName()).info("connection closed, deque size: " + connections.size());

            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(MyMyConnectionPool.class.getName()).error(ex);
            }
        }
    }

    /**
     * set delay method
     *
     * @param delay the delay to set
     */
    public void setDelay(int delay) {
        if (delay > 0) {
            this.delay = delay;
        }
    }

    /**
     *
     * @return
     */
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     *
     * @return
     */
    public int getCurrentPoolSize() {
        return currentPoolSize;
    }

}
