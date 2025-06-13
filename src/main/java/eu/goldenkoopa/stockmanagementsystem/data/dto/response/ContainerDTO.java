package eu.goldenkoopa.stockmanagementsystem.data.dto.response;

import eu.goldenkoopa.stockmanagementsystem.data.Container;
import eu.goldenkoopa.stockmanagementsystem.data.Group;
import java.time.LocalDateTime;
import java.util.List;

public record ContainerDTO(
    Integer id,
    String name,
    String createdBy,
    String updateBy,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String data,
    List<Integer> groups,
    String server,
    String notes) {

  public static ContainerDTO from(Container container) {
    return new ContainerDTO(
        container.getId(),
        container.getName(),
        container.getCreatedBy(),
        container.getUpdatedBy(),
        container.getCreatedAt(),
        container.getUpdatedAt(),
        container.getData(),
        container.getGroups().stream().map(Group::getId).toList(),
        container.getServer(),
        container.getNotes());
  }
}
