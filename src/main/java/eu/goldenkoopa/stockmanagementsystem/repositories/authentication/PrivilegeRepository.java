package eu.goldenkoopa.stockmanagementsystem.repositories.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

  Privilege findByName(String name);
}
