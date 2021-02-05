package com.example.event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Random;

@NoArgsConstructor
@Getter
@ToString
public class SubEvent {

    private String id;
    private String type;
    private boolean check;


    @Builder
    private SubEvent(String id, String type, boolean check) {
        this.id = id;
        this.type = type;
        this.check = check;
    }

    public static SubEvent createType1(){

        return SubEvent.builder()
                .id(String.valueOf(new Random().nextInt(10000)))
                .type("type1")
                .check(false)
                .build();
    }

    public static SubEvent createType2(){

        return SubEvent.builder()
                .id(String.valueOf(new Random().nextInt(10000)))
                .type("type2")
                .check(true)
                .build();
    }
}
