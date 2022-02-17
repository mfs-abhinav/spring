package com.abhi.springparallelprocessing.controller;

import com.abhi.springparallelprocessing.entity.User;
import com.abhi.springparallelprocessing.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/users", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws InterruptedException {
        logger.info("User upload starts " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        for (MultipartFile file : files) {
            userService.saveUsers(file);
        }
        long endTime = System.currentTimeMillis();
        logger.info("Total time taken to insert data by thread " + Thread.currentThread().getName() + " is " + (endTime - startTime));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<User>> getUsers() {
        logger.info("User fetch starts " + Thread.currentThread().getName());
        long startTime = System.currentTimeMillis();
        CompletableFuture<List<User>> task1 = userService.getUsers(0, 1000);
        CompletableFuture<List<User>> task2 = userService.getUsers(1000, 2000);
        CompletableFuture<List<User>> task3 = userService.getUsers(2000, 3000);

        CompletableFuture<Void> completableFutureUsers = CompletableFuture.allOf(task1, task2, task3);
        completableFutureUsers.join();
//        List<List<User>> users = Stream.of(task1, task2, task3).map(CompletableFuture::join).collect(Collectors.toList());
//        List<User> results = users.stream().flatMap((user) -> user.stream()).collect(Collectors.toList());
        List<User> users = Stream.of(task1, task2, task3)
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        long endTime = System.currentTimeMillis();
        logger.info("Total time taken to fetch user by thread " + Thread.currentThread().getName() + " is " + (endTime - startTime));
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
