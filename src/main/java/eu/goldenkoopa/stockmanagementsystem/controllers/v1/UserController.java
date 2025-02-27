package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.dto.authentication.UserWithApiKeyDto;
import eu.goldenkoopa.stockmanagementsystem.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Secured({"ROLE_ADMIN"})
public class UserController {

  private UserService userService;

  @GetMapping()
  public List<UserWithApiKeyDto> getAllUsers() {
    return userService.getAllUsers()
        .stream()
        .map(UserWithApiKeyDto::from)
        .toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserWithApiKeyDto> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(
        UserWithApiKeyDto.from(userService.getUserById(id)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }
}
