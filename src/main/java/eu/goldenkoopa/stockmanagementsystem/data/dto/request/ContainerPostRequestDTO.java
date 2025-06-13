package eu.goldenkoopa.stockmanagementsystem.data.dto.request;

import jakarta.validation.constraints.NotNull;

public record ContainerPostRequestDTO(
  @NotNull(message = "field 'name' must not be null") String name,
  @NotNull(message = "field 'data' must not be null") String data,
  @NotNull(message = "field 'server' must not be null") String server) {
}
