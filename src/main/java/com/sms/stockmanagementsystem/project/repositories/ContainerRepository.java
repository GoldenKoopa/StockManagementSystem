package com.sms.stockmanagementsystem.project.repositories;

import com.sms.stockmanagementsystem.project.data.Container;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerRepository extends JpaRepository<Container, String> {

    List<Container> findByServer(String server);

    List<Container> findByNameAndServer(String name, String server);
}
