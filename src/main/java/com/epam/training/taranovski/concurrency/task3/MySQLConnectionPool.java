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
public class MySQLConnectionPool {

    private static MySQLConnectionPool instance;

    private static final int DEFAULT_SIZE = 10;
    private static final int DEFAULT_DELAY = 50;

    private Deque<Connection> connections = new LinkedList<>();

    private int maxPoolSize = 0;
    private int delay = 0;

    private int currentPoolSize = 0;
    private static String driverName;
    private static String url;

    /**
     * private constructor for singleton pattern
     *
     * @param size wanted pool size
     * @param delay wanted delay for connection retries
     */
    private MySQLConnectionPool(int size, int delay) {
        this.maxPoolSize = size;
        this.delay = delay;
    }

    /**
     * set a size of pool
     *
     * @param size wanted size
     */
    public void setSize(int size) {
        synchronized (MySQLConnectionPool.class) {
            // if size <= 0 skipping
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
                                Logger.getLogger(MySQLConnectionPool.class.getName()).error(ex);
                            }
                        }
                    } else {
                        currentPoolSize = size;
                    }
                }
                this.maxPoolSize = size;
            }
        }
    }

    /**
     * get instance method
     *
     * @return an instance of connection pool
     */
    public static MySQLConnectionPool getInstance() {
        if (instance == null) {
            // thread safe singleton
            synchronized (MySQLConnectionPool.class) {
                if (instance == null) {
                    instance = new MySQLConnectionPool(DEFAULT_SIZE, DEFAULT_DELAY);
                    driverName = InitConfiguration.getInstance()
                            .getProperty(InitConfiguration.DATABASE_DRIVER_NAME);
                    url = InitConfiguration.getInstance().getProperty(InitConfiguration.DATABASE_URL);
                    try {
                        Class.forName(driverName);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MySQLConnectionPool.class.getName()).error(ex);
                    }
                }
            }
        }
        return instance;
    }

    /**
     * get connection method
     *
     * @return connection
     */
    public Connection getConnection() {
        while (true) {
            synchronized (MySQLConnectionPool.class) {
                if (!connections.isEmpty()) {
                    Logger.getLogger(MySQLConnectionPool.class.getName()).info("connection from deque, deque size: " + connections.size());
                    return connections.pollFirst();
                } else if (currentPoolSize < maxPoolSize) {
                    try {
                        currentPoolSize++;
                        Logger.getLogger(MySQLConnectionPool.class.getName()).info("new connection, deque size: " + connections.size());

                        return DriverManager.getConnection(url);
                    } catch (SQLException ex) {
                        Logger.getLogger(MySQLConnectionPool.class.getName()).error(ex);
                    }
                }
            }
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                Logger.getLogger(MySQLConnectionPool.class.getName()).error(ex);
            }
            //return null;
        }
    }

    /**
     * puts a used connection to deque
     *
     * @param connection connection to put
     */
    public void free(Connection connection) {
        synchronized (MySQLConnectionPool.class) {
            if (connections.size() < maxPoolSize) {
                Logger.getLogger(MySQLConnectionPool.class.getName()).info("connection put to deque, deque size: " + connections.size());

                connections.addFirst(connection);
            } else {
                Logger.getLogger(MySQLConnectionPool.class.getName()).info("connection closed, deque size: " + connections.size());

                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MySQLConnectionPool.class.getName()).error(ex);
                }
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
}
