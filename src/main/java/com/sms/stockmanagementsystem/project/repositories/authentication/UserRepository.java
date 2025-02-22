package com.sms.stockmanagementsystem.project.repositories.authentication;

import com.sms.stockmanagementsystem.project.data.authentication.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
