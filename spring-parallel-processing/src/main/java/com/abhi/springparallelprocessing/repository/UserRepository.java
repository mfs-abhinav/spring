package com.abhi.springparallelprocessing.repository;

import com.abhi.springparallelprocessing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    public List<User> findByIdBetween(Long id, Long id2);
}
