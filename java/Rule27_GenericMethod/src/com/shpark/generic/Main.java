package com.shpark.generic;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.lang.String;
import java.lang.Comparable;
import java.util.List;
import java.util.ArrayList;

class Main {

    public static final void main(String argv[]) {
        Main m = new Main();
        m.example_generic_function();        
        m.example_generic_singleton();
        m.example_recursive_type_bound();
    }

    void example_generic_function() {
        Set<String> guys = new HashSet<>(Arrays.asList("Tom", "Dick", "Harry"));
        Set<String> stooges = new HashSet<>(Arrays.asList("Larry", "Moe", "Curly"));
        Set<String> aflCio = union(guys, stooges);
        System.out.println(aflCio);
    }

    // ensure no warning
    public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
        Set<E> result = new HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }

    void example_generic_singleton() {
        String[] strings = { "jute", "hemp", "nylon" };
        UnaryFunction<String> sameString = identityFunction();
        for (String s : strings) {
            System.out.println(sameString.apply(s));
        }

        Number[] numbers = { 1, 2.0, 3L }; // integer, float, long
        UnaryFunction<Number> sameNumber = identityFunction();
        for (Number n : numbers) {
            System.out.println(sameNumber.apply(n));
        }
    }
    // generic singleton factory pattern
    private static UnaryFunction<Object> IDENTITY_FUNCTION = new UnaryFunction<Object>() {
        public Object apply(Object arg) { return arg; }
    };

    // I'm sure about using unchecked casting(SuppressWarnings).
    // The reasons why, In "identityFunction" function, IDENTITY_FUNCTION is a private variable.
    // And there are no manipulation codes. Accordingly, I simply decide to suppress warnings.
    @SuppressWarnings("unchecked")
    public static <T> UnaryFunction<T> identityFunction() {
        return (UnaryFunction<T>) IDENTITY_FUNCTION;
    }

    void example_recursive_type_bound() {
        List<ReverseInteger> list = new ArrayList<>();
        list.add(new ReverseInteger(-13));
        list.add(new ReverseInteger(3));
        list.add(new ReverseInteger(-9));

        ReverseInteger value = max(list);   
        System.out.println(value.getValue());
    }

    class ReverseInteger implements Comparable<ReverseInteger> {
        private int value;

        public int compareTo(ReverseInteger rvalue) {
            if ( this.value > rvalue.getValue() ) {
                return -1;
            } else if ( this.value == rvalue.getValue() ) {
                return 0;
            } else {
                return 1;
            }
        }

        ReverseInteger(int i) {
            value = i;
        }
        public int getValue() {
            return value;
        }
    }

    // recursive generic comparator
    public static <T extends Comparable<T>> T max(List<T> list) {
        Iterator<T> i = list.iterator();
        T result = i.next();
        while (i.hasNext()) {
            T t = i.next();
            if (t.compareTo(result) > 0) {
                result = t;
            }
        }
        return result;
    }
}
