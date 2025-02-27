package eu.goldenkoopa.stockmanagementsystem.services;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;
import eu.goldenkoopa.stockmanagementsystem.repositories.authentication.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * UserService
 */
@Service
public class UserService {

  @Autowired UserRepository userRepository;

  @Autowired PasswordEncoder passwordEncoder;

  /**
   * Updates the password for a given user if the current password is correct.
   *
   * @param username        The username of the user.
   * @param currentPassword The current password of the user.
   * @param newPassword     The new password to be set.
   * @throws Exception if the current password is incorrect or the user is not
   *                   found.
   */
  public void updatePassword(String username, String currentPassword,
                             String newPassword) throws Exception {
    User user = userRepository.findByUsername(username).orElseThrow(
        () -> new UsernameNotFoundException("User not found"));

    if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
      throw new Exception("Current password is incorrect");
    }

    user.setPassword(passwordEncoder.encode(newPassword));
    userRepository.save(user);
  }

  /**
   * Checks if a user exists by username.
   *
   * @param username The username to check.
   * @return true if the user exists, false otherwise.
   */
  public boolean existsByUsername(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(
        () -> new RuntimeException("id not found"));
  }

  /**
   * Saves a user to the repository.
   *
   * @param user The user to save.
   */
  public void saveUser(User user) { userRepository.save(user); }

  /**
   * Deletes a user from the repository.
   *
   * @param id The id of the user to delete.
   */
  public void deleteUser(Long id) { userRepository.deleteById(id); }

  /**
   * Gets all users from the repository.
   */
  public List<User> getAllUsers() { return userRepository.findAll(); }
}
