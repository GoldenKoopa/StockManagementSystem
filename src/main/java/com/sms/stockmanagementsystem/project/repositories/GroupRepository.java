package com.sms.stockmanagementsystem.project.repositories;

import com.sms.stockmanagementsystem.project.data.Group;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {

  List<Group> findByName(String name);
}
