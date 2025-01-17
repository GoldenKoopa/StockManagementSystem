package com.sms.stockmanagementsystem.project.repositories.authentication;

import com.sms.stockmanagementsystem.project.data.authentication.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
