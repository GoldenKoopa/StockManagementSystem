package eu.goldenkoopa.stockmanagementsystem.services;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;
import eu.goldenkoopa.stockmanagementsystem.repositories.authentication.ApiKeyRepository;
import eu.goldenkoopa.stockmanagementsystem.repositories.authentication.PrivilegeRepository;
import eu.goldenkoopa.stockmanagementsystem.repositories.authentication.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** ApiKeyService */
@Service
public class ApiKeyService {

  private ApiKeyRepository apiKeyRepository;
  private UserRepository userRepository;
  private PrivilegeRepository privilegeRepository;

  public ApiKeyService(
      ApiKeyRepository apiKeyRepository,
      UserRepository userRepository,
      PrivilegeRepository privilegeRepository) {
    this.apiKeyRepository = apiKeyRepository;
    this.userRepository = userRepository;
    this.privilegeRepository = privilegeRepository;
  }

  /**
   * @return list of all api keys
   */
  public List<ApiKey> getAllApiKeys() {
    List<ApiKey> apiKeys = apiKeyRepository.findAll();
    return apiKeys;
  }

  @Transactional
  public ApiKey generateNewApiKey(
      List<Long> privilegeIds, Long expirationTime, UserDetails userDetails) {
    if (privilegeIds == null || privilegeIds.isEmpty()) {
      throw new IllegalArgumentException("Privilege IDs cannot be null or empty");
    }
    User user =
        userRepository
            .findByUsername(userDetails.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("username not found"));

    ApiKey apiKey = new ApiKey();
    apiKey.setUser(user);
    apiKey.setPrivileges(
        privilegeIds.stream()
            .map(
                id ->
                    privilegeRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException("privilege id not found: " + id)))
            .toList());
    apiKey.setExpirationDate(LocalDate.now().plusDays(expirationTime));
    apiKey.setKey(generateUniqueApiKey());

    ApiKey apiKeyResponse = apiKeyRepository.save(apiKey);
    return apiKeyResponse;
  }

  private String generateUniqueApiKey() {
    String key;
    do {
      key = UUID.randomUUID().toString();
    } while (apiKeyRepository.existsByKey(key));
    return key;
  }

  public ApiKey getApiKeyByKey(String string) {
    return apiKeyRepository
        .findByKey(string)
        .orElseThrow(() -> new RuntimeException("api key not found"));
  }
}
