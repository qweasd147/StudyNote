package com.example.demo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Pair<K, V> {

    private final K key;
    private final V value;

    public static <K, V> Pair<K,V> of(K key, V value){
        return new Pair<>(key, value);
    }

    public K getLeft(){
        return this.getKey();
    }

    public V getRight(){
        return this.getValue();
    }
}
