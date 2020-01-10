package com.example.study;

public class MathTest {



    public static void main(String[] args) {

        long a = Math.round(1.5);
        long a1 = Math.round(1.4);
        long a3 = Math.round(-1.4);
        long a4 = Math.round(-1.5);
        long a5 = Math.round(-1.6);
        long a6 = Math.round(-1.500000001);

        System.out.println(a);
        System.out.println(a1);
        System.out.println(a3);
        System.out.println(a4);
        System.out.println(a5);
        System.out.println(a6);
    }
}
