package com.example.event.listener;

import com.example.event.Event;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class EventSubscriber {

    @EventListener
    @Async
    public void process1(Event event){

        System.out.println("이벤트 처리111111 : " + event.toString());
    }

    @EventListener
    @Async
    public void process2(Event event){

        System.out.println("이벤트 처리222222 : " + event.toString());
    }
}
