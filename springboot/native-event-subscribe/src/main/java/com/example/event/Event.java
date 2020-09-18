package com.example.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Random;

@NoArgsConstructor
@Getter
@ToString
public class Event {

    private String id;
    private String type;
    private boolean check;


    @Builder
    private Event(String id, String type, boolean check) {
        this.id = id;
        this.type = type;
        this.check = check;
    }

    public static Event createType1(){

        return Event.builder()
                .id(String.valueOf(new Random().nextInt(10000)))
                .type("type1")
                .check(false)
                .build();
    }

    public static Event createType2(){

        return Event.builder()
                .id(String.valueOf(new Random().nextInt(10000)))
                .type("type2")
                .check(true)
                .build();
    }
}
