package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.services.UserService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class PublicUserController {

  private UserService userService;

  @PostMapping("/password")
  public ResponseEntity<Void> changePassword(
      @RequestBody PasswordChangeRequest passwordChangeRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    userService.updatePassword(
        userDetails.getUsername(),
        passwordChangeRequest.oldPassword(),
        passwordChangeRequest.newPassword());
    return ResponseEntity.noContent().build();
  }

  private record PasswordChangeRequest(
      @NotNull(message = "old password cannot be null")
          @NotEmpty(message = "old password cannot be empty")
          String oldPassword,
      @NotNull(message = "new password cannot be null")
          @NotEmpty(message = "new password cannot be empty")
          String newPassword) {}

  public PublicUserController(UserService userService) {
    this.userService = userService;
  }
}
