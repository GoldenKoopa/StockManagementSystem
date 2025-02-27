package eu.goldenkoopa.stockmanagementsystem.data.dto.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import java.util.List;

/**
 * A data transfer object (DTO) representing an API key with associated
 * privileges.
 * This record encapsulates the essential information of an API key, including
 * its unique identifier, the key itself, and a list of privileges associated
 * with it.
 */
public record ApiKeyDto(Long id, String key, List<PrivilegeDto> privileges) {

  /**
   * Creates an ApiKeyDto from an ApiKey entity.
   *
   * @param apiKey The ApiKey entity to convert
   * @return A new ApiKeyDto instance containing the API key's information and
   *         associated privileges
   * @throws NullPointerException if the apiKey parameter is null
   */
  public static ApiKeyDto from(ApiKey apiKey) {
    return new ApiKeyDto(
        apiKey.getId(), apiKey.getKey(),
        apiKey.getPrivileges().stream().map(PrivilegeDto::from).toList());
  }
}
