package eu.goldenkoopa.stockmanagementsystem.data.dto.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import java.util.List;

/**
 * A data transfer object (DTO) representing an API key with associated user
 * information and privileges. This record encapsulates the essential
 * information of an API key, including its unique identifier, the key itself, a
 * list of privileges, and the associated user.
 */
public record ApiKeyWithUserDto(Long id, String key,
                                List<PrivilegeDto> privileges, UserDto user) {

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
  public static ApiKeyWithUserDto from(ApiKey apiKey) {
    return new ApiKeyWithUserDto(
        apiKey.getId(), apiKey.getKey(),
        apiKey.getPrivileges().stream().map(PrivilegeDto::from).toList(),
        UserDto.from(apiKey.getUser()));
  }
}
