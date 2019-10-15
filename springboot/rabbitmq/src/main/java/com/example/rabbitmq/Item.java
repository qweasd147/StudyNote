package com.example.rabbitmq;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Item {

    private String data;
    private Integer number;

    @Builder
    private Item(String data, Integer number){
        this.data = data;
        this.number = number;
    }
}
