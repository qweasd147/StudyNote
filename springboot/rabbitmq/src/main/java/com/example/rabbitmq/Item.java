package com.example.rabbitmq;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Item {

    private String data;
    private Integer number;
    private boolean isThrow;

    @Builder
    private Item(String data, Integer number, boolean isThrow){
        this.data = data;
        this.number = number;
        this.isThrow = isThrow;
    }
}
