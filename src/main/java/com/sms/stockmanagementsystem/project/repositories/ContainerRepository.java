package com.sms.stockmanagementsystem.project.repositories;

import com.sms.stockmanagementsystem.project.data.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, String> {

}
