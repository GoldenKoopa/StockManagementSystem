package eu.goldenkoopa.stockmanagementsystem.repositories;

import eu.goldenkoopa.stockmanagementsystem.data.Group;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Integer> {

  List<Group> findByName(String name);
}
