package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;
import eu.goldenkoopa.stockmanagementsystem.data.dto.authentication.UserDto;
import eu.goldenkoopa.stockmanagementsystem.data.dto.authentication.UserWithApiKeyDto;
import eu.goldenkoopa.stockmanagementsystem.services.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Secured({"ROLE_ADMIN"})
public class UserController {

  private UserService userService;

  @GetMapping()
  public List<UserWithApiKeyDto> getAllUsers() {
    return userService.getAllUsers().stream().map(UserWithApiKeyDto::from).toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserWithApiKeyDto> getUser(@PathVariable Long id) {
    UserWithApiKeyDto user = UserWithApiKeyDto.from(userService.getUserById(id));
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
  }

  @PostMapping()
  public ResponseEntity<UserDto> createUser(@RequestBody UserPostRequest userPostRequest) {
    User user = userService.createUser(userPostRequest.username(), userPostRequest.password());
    return new ResponseEntity<UserDto>(UserDto.from(user), HttpStatus.CREATED);
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

  private record UserPostRequest(
      @NotNull(message = "username cannot be null") @NotEmpty(message = "username cannot be empty")
          String username,
      @NotNull(message = "password cannot be null") @NotEmpty(message = "password cannot be empty")
          String password) {}
}
