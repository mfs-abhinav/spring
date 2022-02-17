package com.abhi.springaopdemo.service;

import org.springframework.stereotype.Component;

@Component
public class HiByeService {
    public void sayHi() {
        System.out.println("Hi");
    }

    public void sayHi(String name) {
        System.out.println("Hi " + name);
    }

    public void sayBye() {
        System.out.println("Bye");
    }

    public String doSomething(String input) {
        System.out.println("Doing something");
        return "Doing " + input;
    }
}
