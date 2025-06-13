package eu.goldenkoopa.stockmanagementsystem.data.dto.response.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import java.util.List;

/**
 * A data transfer object (DTO) representing an API key with associated user
 * information and privileges. This record encapsulates the essential
 * information of an API key, including its unique identifier, the key itself, a
 * list of privileges, and the associated user.
 */
public record ApiKeyWithUserDTO(Long id, String key,
                                List<PrivilegeDTO> privileges, UserDTO user) {

  /**
   * Creates an ApiKeyWithUserDto from an ApiKey entity.
   *
   * @param apiKey The ApiKey entity to convert
   * @return A new ApiKeyWithUserDto instance containing the API key's
   *         information, privileges, and associated user
   * @throws IllegalArgumentException if the apiKey is null, or if the key is
   *     null
   *                                  or blank
   * @throws NullPointerException     if the privileges or user in the apiKey
   *     are
   *                                  null
   */
  public static ApiKeyWithUserDTO from(ApiKey apiKey) {
    return new ApiKeyWithUserDTO(
        apiKey.getId(), apiKey.getKey(),
        apiKey.getPrivileges().stream().map(PrivilegeDTO::from).toList(),
        UserDTO.from(apiKey.getUser()));
  }
}
