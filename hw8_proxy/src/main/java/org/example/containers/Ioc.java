package org.example.containers;

import org.example.annotations.Log;
import org.example.api.TestLoggingInterface;
import org.example.implementations.TestLogging;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ioc {
    private Ioc() {
    }

    public static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new LoggingInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class LoggingInvocationHandler implements InvocationHandler {
        private TestLoggingInterface testLogging;

        public LoggingInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getAnnotation(Log.class) == null)
                return method.invoke(testLogging, args);

            System.out.println("executed method: " + method.getName() + ", params: " + formParams(args));
            return method.invoke(testLogging, args);

        }

        public String formParams(Object[] args) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i].toString());
                if (i != args.length - 1)
                    builder.append(", ");
            }
            return builder.toString();
        }
    }
}
