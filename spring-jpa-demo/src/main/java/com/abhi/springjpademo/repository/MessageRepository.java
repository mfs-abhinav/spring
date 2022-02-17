package com.abhi.springjpademo.repository;

import com.abhi.springjpademo.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
