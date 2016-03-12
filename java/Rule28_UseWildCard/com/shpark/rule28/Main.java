package com.shpark.rule28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.printLine();
        m.testdrive1();
        m.printLine();
        m.testdrive2();
        m.printLine();
    }

    private void printLine() {
        System.out.println("----------------------------------");
    }

    private void testdrive1() {
        // pushAll test
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Stack<Integer> stack = new Stack<>();       // Stack<Integer>
        stack.pushAll(list);

        while (!stack.isEmpty()) {
            System.out.println("" + stack.pop());
        }

        // popAll test
        List<Integer> trunk = new ArrayList<>();    // List<Integer>
        stack.pushAll(list);
        stack.popAll(trunk);

        for ( Integer i : trunk ) {
            System.out.println("" + i);
        }
    }

    private void testdrive2() {
        // pushAll test
        List<Integer> integers = new ArrayList<>();
        integers.add(1);

        List<Double> doubles = new ArrayList<>();
        doubles.add(2.0);

        List<Long> longs = new ArrayList<>();
        longs.add(3L);

        Stack<Number> stack = new Stack<>();        // Stack<Number>
        stack.pushAll(integers);
        stack.pushAll(doubles);
        stack.pushAll(longs);

        while (!stack.isEmpty()) {
            System.out.println("" + stack.pop());
        }

        // popAll test
        List<Number> trunk = new ArrayList<>();     // List<Number>
        stack.pushAll(integers);
        stack.pushAll(doubles);
        stack.pushAll(longs);
        stack.popAll(trunk);

        for ( Number n : trunk ) {
            System.out.println("" + n);
        }
    }
}
