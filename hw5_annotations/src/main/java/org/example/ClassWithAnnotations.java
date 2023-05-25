package org.example;

public class ClassWithAnnotations {
    @Before
    public void before() {
        System.out.println("process before() method with annotation before");
    }

    @After
    public void after() {
        System.out.println("process after() method with annotation after");
    }

    @Test
    public void method1() {
        System.out.println("process method1() method with annotation test");

    }

    @Test
    public void method2() {
        System.out.println("process method2() method with annotation test");
    }

    @Test
    public void method3() {
        System.out.println("process method3() method with annotation test");
        throw new ArithmeticException();

    }

    @Test
    public void method4() {
        System.out.println("process method4() method with annotation test");
    }
}
