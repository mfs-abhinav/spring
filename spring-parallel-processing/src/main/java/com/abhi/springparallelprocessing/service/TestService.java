package com.abhi.springparallelprocessing.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class TestService {

    @Async
    public void print() throws InterruptedException {
        System.out.println("async processing starts");
        try {
            Thread.sleep(10000);
            int a = 1;
            int b = 0;
            int c = 0;
            c = a/b;
            System.out.println("C value is "+c);
            System.out.println("async processing ends");
//            return new AsyncResult<>(c);
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            return new AsyncResult<>(0);
        }

    }
}
