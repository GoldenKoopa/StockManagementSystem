package eu.goldenkoopa.stockmanagementsystem.repositories;

import eu.goldenkoopa.stockmanagementsystem.data.Container;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContainerRepository extends JpaRepository<Container, String> {

  List<Container> findByServer(String server);

  Optional<Container> findByNameAndServer(String name, String server);

  boolean existsByNameAndServer(String name, String server);
}
