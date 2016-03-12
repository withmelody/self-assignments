package com.shpark.rule28;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Stack<E> {
    private List<E> list = new ArrayList<>();

    public Stack() {}

    public void push(E e) {
        list.add(e);
    }

    public E pop() {
        if ( list.size() > 0 ) {
            return list.remove(list.size() - 1);
        }
        return null;
    }

    public boolean isEmpty() {
        return list.size() == 0 ? true : false;
    }

/*
    // for testdrive1()
    public void pushAll(Iterable<E> src) {
        for ( E e : src ) {
            push(e);
        }
    }
*/

    // to support testdrive2() including testdrive1()
    public void pushAll(Iterable<? extends E> src) {
        for ( E e : src ) {
            push(e);
        }
    }

/*
    public void popAll(Collection<E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }
*/

    // to support testdrive2() including testdrive1()
    public void popAll(Collection<? super E> dst) {
        while (!isEmpty()) {
            dst.add(pop());
        }
    }
}
