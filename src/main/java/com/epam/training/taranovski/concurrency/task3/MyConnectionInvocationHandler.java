/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epam.training.taranovski.concurrency.task3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 *
 * @author Oleksandr_Taranovsky
 */
public class MyConnectionInvocationHandler implements InvocationHandler {

    private Connection wrapped;
    private long lastTimeUsed;

    /**
     *
     * @param wrapped
     */
    private MyConnectionInvocationHandler(Connection wrapped) {
        this.wrapped = wrapped;
        lastTimeUsed = System.currentTimeMillis();
    }

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (wrapped == null) {
            throw new RuntimeException("connection is closed");
        }

        if ("close".equals(method.getName())) {
            wrapped = null;
            return null;
        }

        lastTimeUsed = System.currentTimeMillis();
        return method.invoke(wrapped, args);
    }

    /**
     *
     * @return
     */
    public long getLastTimeUsed() {
        return lastTimeUsed;
    }

    /**
     *
     * @param toWrap
     * @return
     */
    public static Connection createBuilderRobot(Connection toWrap) {
        return (Connection) (Proxy.newProxyInstance(Connection.class.getClassLoader(),
                new Class[]{Connection.class},
                new MyConnectionInvocationHandler(toWrap)));
    }

}
