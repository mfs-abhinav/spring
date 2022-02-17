package com.abhi.springaopdemo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HiByeServiceTest {

    @Autowired
    private HiByeService hiByeService;

    @Test
    public void testHi() {
        hiByeService.sayHi();
    }

    @Test
    public void testHiWithArgument() {
        hiByeService.sayHi("Abhinav");
    }

    @Test
    public void testBye() {
        hiByeService.sayBye();
    }

    @Test
    public void testDoSomething() {
        hiByeService.doSomething("Abhi");
    }
}

