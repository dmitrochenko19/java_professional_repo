package com.example.myapp;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Lists;

class HelloOtus{
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        System.out.println(Lists.reverse(list));
    }
}