package org.example;

public class CalculatorTest {
    Calculator calculator;

    @Before
    public void init() {
        calculator = new Calculator(5, 4);
    }

    @After
    public void delete() {
        calculator = null;
    }

    @Test
    public void sumTest() {
        int result = calculator.sum();
        if (!(result == 9))
            throw new RuntimeException("sumTest method failed: expected 9 , actual " + result);
    }

    @Test
    public void subtractTest() {
        int result = calculator.subtract();
        if (!(result == 1))
            throw new RuntimeException("subtractTest method failed: expected 1 , actual " + result);
    }

    @Test
    public void multiplyTest() {
        int result = calculator.multiply();
        if (!(result == 20))
            throw new RuntimeException("multiplyTest method failed: expected 20 , actual " + result);
    }

    @Test
    public void divideTest() {
        int result = calculator.divide();
        if (!(result == 1))
            throw new RuntimeException("divideTest method failed: expected 1 , actual " + result);
    }
}
