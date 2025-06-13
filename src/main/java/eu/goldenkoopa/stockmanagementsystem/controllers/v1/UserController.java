package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.User;
import eu.goldenkoopa.stockmanagementsystem.data.dto.request.UserPostRequestDTO;
import eu.goldenkoopa.stockmanagementsystem.data.dto.response.authentication.UserDTO;
import eu.goldenkoopa.stockmanagementsystem.data.dto.response.authentication.UserWithApiKeyDTO;
import eu.goldenkoopa.stockmanagementsystem.services.UserService;

import java.util.List;

import jakarta.validation.Valid;
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
  public List<UserWithApiKeyDTO> getAllUsers() {
    return userService.getAllUsers().stream().map(UserWithApiKeyDTO::from).toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserWithApiKeyDTO> getUser(@PathVariable Long id) {
    UserWithApiKeyDTO user = UserWithApiKeyDTO.from(userService.getUserById(id));
    if (user == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(user);
  }

  @PostMapping()
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserPostRequestDTO userPostRequest) {
    User user = userService.createUser(userPostRequest.username(), userPostRequest.password());
    return new ResponseEntity<UserDTO>(UserDTO.from(user), HttpStatus.CREATED);
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
