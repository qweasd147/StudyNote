package com.example.event;

import com.example.event.publisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventPublisher eventPublisher;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void createEvent(){

        this.eventPublisher.publish(Event.createType1());
        this.eventPublisher.publish(Event.createType2());
        this.eventPublisher.publish(SubEvent.createType2());
    }
}
