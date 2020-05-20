package com.rxjava.demo.rxjava;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Async {

    public static void main(String[] args) throws InterruptedException {

        Async async = new Async();

        //async.subscribeOn();
        async.observeOn();
    }

    public void subscribeOn() throws InterruptedException {

        Flowable.just(1, 2, 3, 4, 5)
                .subscribeOn(Schedulers.newThread())
                .subscribe((number)->{
                    String threadName = Thread.currentThread().getName();
                    System.out.println("thread name : " + threadName + ", number : "+number);
                });


        Thread.sleep(500);
    }

    public void observeOn() throws InterruptedException {

        @NonNull Flowable<Long> flowable = Flowable.interval(300L, TimeUnit.MILLISECONDS)
            .onBackpressureDrop((dropData) -> System.out.println("데이터 버림 " + dropData));

        flowable.observeOn(Schedulers.computation(), false, 2)
            .subscribe((data)->{
                String threadName = Thread.currentThread().getName();
                System.out.println("thread name : " + threadName + ", number : "+data);

                Thread.sleep(1000L);

            });

        Thread.sleep(7000L);
    }
}
