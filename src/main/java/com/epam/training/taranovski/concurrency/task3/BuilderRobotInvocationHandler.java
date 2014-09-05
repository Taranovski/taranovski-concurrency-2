package com.epam.training.taranovski.concurrency.task3;

import java.lang.reflect.Proxy;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class BuilderRobotInvocationHandler implements InvocationHandler {

    private Robot wrapped;

    public BuilderRobotInvocationHandler(Robot r) {
        wrapped = r;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if ("workOn".equals(method.getName())) {
            args[1] = Tool.RATCHET;
        }
        return method.invoke(wrapped, args);
    }

    public static Robot createBuilderRobot(Robot toWrap) {
        return (Robot) (Proxy.newProxyInstance(Robot.class.getClassLoader(),
                new Class[]{Robot.class},
                new BuilderRobotInvocationHandler(toWrap)));
    }

    public static final void main(String[] args) {
        Robot r = createBuilderRobot(new MyRobot());
        r.workOn("scrap", Tool.CUTTING_TORCH);
    }
}






