package eu.goldenkoopa.stockmanagementsystem.data.dto.response.authentication;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;

/**
 * A data transfer object (DTO) representing a user. This record encapsulates the essential
 * information of a user, including their unique identifier, first name, last name, and username.
 */
public record UserDTO(Long id, String username) {

  /**
   * Creates a UserDto from a User entity.
   *
   * @param user The User entity to convert
   * @return A new UserDto instance containing the user's information
   * @throws IllegalArgumentException if the user is null or if any required field is null or blank
   */
  public static UserDTO from(User user) {
    return new UserDTO(user.getId(), user.getUsername());
  }
}
