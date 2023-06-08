package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) throws Exception {
        run("org.example.CalculatorTest");
        System.out.println("----------------");
        run("org.example.ClassWithAnnotations");
    }

    /**
     * input class should have default constructor
     */
    public static void run(String className) {
        int countOk = 0;
        int countFailed = 0;
        Class<?> clazz = createClassForName(className);
        if (clazz == null) {
            System.out.println("class name is wrong");
            return;
        }
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> beforeMethods = Arrays.stream(methods).filter(m -> m.getAnnotation(Before.class) != null).toList();
        List<Method> testMethods = Arrays.stream(methods).filter(m -> m.getAnnotation(Test.class) != null).toList();
        List<Method> afterMethod = Arrays.stream(methods).filter(m -> m.getAnnotation(After.class) != null).toList();
        if (testMethods.size() == 0)
            return;
        for (int i = 0; i < testMethods.size(); i++) {
            Object object = null;
            try {
                object = createNewInstance(clazz);
                Method testMethod = testMethods.get(i);
                invokeMethods(beforeMethods, object);
                testMethod.invoke(object);
                countOk++;
            } catch (Exception e) {
                countFailed++;
            } finally {
                if (object != null)
                    invokeMethods(afterMethod, object);
            }
        }
        printResults(testMethods.size(), countOk, countFailed);
    }


    private static void invokeMethods(List<Method> methods, Object object) {
        try {
            for (Method method : methods) {
                method.invoke(object);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> createClassForName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private static void printResults(int countAll, int countOk, int countFailed) {
        System.out.println("Результаты тестирования");
        System.out.println("Общее количество тестов: " + countAll);
        System.out.println("Прошло: " + countOk);
        System.out.println("Упало: " + countFailed);
    }

    private static Object createNewInstance(Class<?> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                 InstantiationException e) {
            System.out.println("something wrong with constructor in your class");
        }
        return null;
    }
}