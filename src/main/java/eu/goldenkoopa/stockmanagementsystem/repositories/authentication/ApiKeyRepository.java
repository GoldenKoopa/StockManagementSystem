package eu.goldenkoopa.stockmanagementsystem.repositories.authentication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

  List<ApiKey> findByUser(User user);

  boolean existsByKey(String key);
}
