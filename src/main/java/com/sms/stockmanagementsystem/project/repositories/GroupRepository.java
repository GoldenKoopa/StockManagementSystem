package com.sms.stockmanagementsystem.project.repositories;

import com.sms.stockmanagementsystem.project.data.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Integer> {

  List<Group> findByName(String name);
}
