package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TempController {

    @GetMapping("/api/health")
    public Object test() throws InterruptedException {


        Thread.sleep(7000);


        Map<String, String> result = new HashMap<>();

        result.put("result", "OK");

        return result;
    }
}
