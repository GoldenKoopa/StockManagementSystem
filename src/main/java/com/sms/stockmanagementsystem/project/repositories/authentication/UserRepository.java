package com.sms.stockmanagementsystem.project.repositories.authentication;


import com.sms.stockmanagementsystem.project.data.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
