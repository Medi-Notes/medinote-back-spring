package com.medinote.medinotebackspring.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldResource {

    @GetMapping("/hello-world")
    public String retrieveHelloWorld() {
        return "Hello World!!";
    }
}
