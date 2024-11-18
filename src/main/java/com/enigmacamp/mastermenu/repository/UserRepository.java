package com.enigmacamp.mastermenu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enigmacamp.mastermenu.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}
