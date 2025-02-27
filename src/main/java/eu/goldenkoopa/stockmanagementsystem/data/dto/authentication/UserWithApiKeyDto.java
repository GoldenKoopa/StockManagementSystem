package eu.goldenkoopa.stockmanagementsystem.data.dto.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;
import java.util.List;

/**
 * A data transfer object (DTO) representing a user with associated API keys.
 * This record encapsulates user information along with a list of their API
 * keys.
 */
public record UserWithApiKeyDto(Long id, String firstName, String lastName,
                                String username, List<ApiKeyDto> apiKeys) {

  /**
   * Creates a UserWithApiKeyDto from a User entity.
   *
   * @param user The User entity to convert
   * @return A new UserWithApiKeyDto instance containing the user's information
   *         and API keys
   * @throws NullPointerException if the user parameter is null
   */
  public static UserWithApiKeyDto from(User user) {
    return new UserWithApiKeyDto(
        user.getId(), user.getFirstName(), user.getLastName(),
        user.getUsername(),
        user.getApiKeys().stream().map(ApiKeyDto::from).toList());
  }
}
