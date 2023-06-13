package org.example.implementations;

import org.example.annotations.Log;
import org.example.api.TestLoggingInterface;

public class TestLogging implements TestLoggingInterface {
    @Override
    public void calculation(int param) {
        System.out.println("method calculation(int) is working");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("method calculation(int, int) is working");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("method calculation(int, int, String) is working");
    }
}
