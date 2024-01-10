package com.example.vehiclerestapi.dao;

import com.example.vehiclerestapi.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);
}
