package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.dto.authentication.UserWithApiKeyDto;
import eu.goldenkoopa.stockmanagementsystem.repositories.authentication.UserRepository;
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

  @Autowired private UserRepository userRepository;

  @GetMapping()
  public List<UserWithApiKeyDto> getAllUsers() {
    return userRepository.findAll()
        .stream()
        .map(UserWithApiKeyDto::from)
        .toList();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    UserService.deleteUser;
    return ResponseEntity.noContent().build();
  }
}
