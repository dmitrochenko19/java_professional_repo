package org.example;

import org.example.api.TestLoggingInterface;
import org.example.containers.Ioc;

public class Main {
    public static void main(String[] args) {
        TestLoggingInterface proxyTestLogging = Ioc.createTestLogging();

        proxyTestLogging.calculation(5);
        proxyTestLogging.calculation(1, 2);
        proxyTestLogging.calculation(2,5,"hello");
    }
}