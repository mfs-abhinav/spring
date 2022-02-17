package com.abhi.springjpademo.controller;

import com.abhi.springjpademo.entity.Message;
import com.abhi.springjpademo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public String getMessage(@PathVariable int id) {
        if (id == 1) {
            throw new RuntimeException("Exception occured");
        }
        return "Hello Hibernate";
    }

    @GetMapping(value = "/save1")
    public void saveMessage1() {
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        TransactionStatus txStatus = platformTransactionManager.getTransaction(txDef);

        for (int i = 0; i < 1000; i++) {
            Message msg = new Message();
            msg.setMessage("Hello JPA " + i);
            entityManager.persist(msg);
        }
        platformTransactionManager.commit(txStatus);
    }

    @GetMapping(value = "/save2")
    @Transactional
    public void saveMessage2() {
        for (int i = 0; i < 1000; i++) {
            Message msg = new Message();
            msg.setMessage("Hello JPA " + i);
            messageRepository.save(msg);
            if (i > 10 && i % 2 == 0) {
                int z = 1/0;
            }
        }
    }

    @GetMapping(value = "/concurrent/save")
    public void saveMessageConcurrent() throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cb = new CyclicBarrier(1000);
        Map<String, Long> map = new HashMap<>();
        for (int i = 0; i < 999; i++) {
            new Thread(() -> {
                try {
                    cb.await();
                    //do stuff
                    Message msg = new Message();
                    msg.setMessage("Hello JPA " + Thread.currentThread().getName());
                    messageRepository.save(msg);
                    map.put(Thread.currentThread().getName(), msg.getId());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        cb.await();
        System.out.println("all threads started");
        Thread.sleep(5000);
        System.out.println("Result after processing");
//        map.entrySet().stream().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
    }
}
