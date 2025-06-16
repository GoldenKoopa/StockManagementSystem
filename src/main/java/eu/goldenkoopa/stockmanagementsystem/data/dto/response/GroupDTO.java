package eu.goldenkoopa.stockmanagementsystem.data.dto.response;

import eu.goldenkoopa.stockmanagementsystem.data.Group;
import java.time.LocalDateTime;

public record GroupDTO(Integer id, LocalDateTime createdAt, String createdBy, String name) {

  public static GroupDTO from(Group group) {
    return new GroupDTO(group.getId(), group.getCreatedAt(), group.getCreatedBy(), group.getName());
  }
}
