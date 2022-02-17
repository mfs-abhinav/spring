package com.abhi.springparallelprocessing.service;

import com.abhi.springparallelprocessing.entity.User;
import com.abhi.springparallelprocessing.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Async
    public CompletableFuture<List<User>> saveUsers(MultipartFile file) throws InterruptedException {
        logger.info("Running thread start: " + Thread.currentThread().getName());
        List<User> users = parseCsvFile(file);
        users = userRepository.saveAll(users);
        logger.info("Running thread end: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCsvFile(final MultipartFile file) {
        List<User> users = new ArrayList<>();
        try (final BufferedReader bf = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                final String[] row = line.split(",");
                final User user = new User();
                user.setName(row[0]);
                user.setEmail(row[1]);
                user.setGender(row[2]);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Async
    public CompletableFuture<List<User>> getUsers(long start, long end) {
        logger.info("Running thread start: " + Thread.currentThread().getName());
        List<User> users = userRepository.findByIdBetween(start, end);
        logger.info("Running thread end: " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(users);
    }
}
