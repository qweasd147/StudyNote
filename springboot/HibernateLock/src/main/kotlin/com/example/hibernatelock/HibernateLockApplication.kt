package com.example.hibernatelock

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HibernateLockApplication

fun main(args: Array<String>) {
    runApplication<HibernateLockApplication>(*args)
}
