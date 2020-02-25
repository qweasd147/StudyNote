package com.rxjava.demo.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;

public class RxSample {

    public static void main(String[] args) {

        @NonNull Flowable<Integer> publisher = Flowable.range(1, 100)
                .filter(value -> value % 2 == 0)
                .map(value -> value * 2);

        publisher.subscribe((value)-> System.out.println(value));
    }
}
