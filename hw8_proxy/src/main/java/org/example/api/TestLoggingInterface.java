package org.example.api;

import org.example.annotations.Log;

public interface TestLoggingInterface {
    @Log
    void calculation(int param);

   @Log
    void calculation(int param1, int param2);

   @Log
    void calculation(int param1, int param2, String param3);

}
