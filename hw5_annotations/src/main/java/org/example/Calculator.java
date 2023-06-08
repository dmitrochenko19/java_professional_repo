package org.example;

public class Calculator {
    private int left;
    private int right;

    public Calculator(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public int sum() {
        return left + right;
    }

    public int subtract() {
        return left - right;
    }

    public int multiply() {
        return left * right;
    }

    public int divide() {
        return left / right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }
}
