package eu.goldenkoopa.stockmanagementsystem.data.dto.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.Privilege;

/**
 * A data transfer object (DTO) representing a privilege.
 * This record encapsulates the essential information of a privilege,
 * including its unique identifier and name.
 */
public record PrivilegeDto(Long id, String name) {

  /**
   * Creates a PrivilegeDto from a Privilege entity.
   *
   * @param privilege The Privilege entity to convert
   * @return A new PrivilegeDto instance containing the privilege's information
   * @throws NullPointerException if the privilege parameter is null
   */
  public static PrivilegeDto from(Privilege privilege) {
    return new PrivilegeDto(privilege.getId(), privilege.getName());
  }
}
