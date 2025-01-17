package com.sms.stockmanagementsystem.project.repositories.authentication;

import com.sms.stockmanagementsystem.project.data.authentication.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);
}
