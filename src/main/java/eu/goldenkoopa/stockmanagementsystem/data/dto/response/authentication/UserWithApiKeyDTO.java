package eu.goldenkoopa.stockmanagementsystem.data.dto.response.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;
import java.util.List;

/**
 * A data transfer object (DTO) representing a user with associated API keys. This record
 * encapsulates user information along with a list of their API keys.
 */
public record UserWithApiKeyDTO(Long id, String username, List<ApiKeyDTO> apiKeys) {

  /**
   * Creates a UserWithApiKeyDto from a User entity.
   *
   * @param user The User entity to convert
   * @return A new UserWithApiKeyDto instance containing the user's information and API keys
   * @throws NullPointerException if the user parameter is null
   */
  public static UserWithApiKeyDTO from(User user) {
    return new UserWithApiKeyDTO(
        user.getId(), user.getUsername(), user.getApiKeys().stream().map(ApiKeyDTO::from).toList());
  }
}
