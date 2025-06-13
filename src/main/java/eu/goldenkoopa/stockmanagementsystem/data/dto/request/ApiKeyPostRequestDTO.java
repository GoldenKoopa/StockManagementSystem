package eu.goldenkoopa.stockmanagementsystem.data.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ApiKeyPostRequestDTO(
  @NotNull(message = "Privilege IDs cannot be null")
  @NotEmpty(message = "Privilege IDs cannot be empty")
  List<@NotNull(message = "Privilege ID cannot be null") Long> privilegeIds,
  @NotNull(message = "Expiration time cannot be null")
  @NotEmpty(message = "Expiration time cannot be empty")
  Long expirationTime) {
}
