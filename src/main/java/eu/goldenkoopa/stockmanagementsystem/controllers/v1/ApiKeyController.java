package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.dto.authentication.ApiKeyWithUserDto;
import eu.goldenkoopa.stockmanagementsystem.services.ApiKeyService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiKeyController
 */
@RestController
@RequestMapping("/api/v1/apikeys")
@Secured({"ROLE_ADMIN"})
public class ApiKeyController {

  private final ApiKeyService apiKeyService;

  @GetMapping
  public ResponseEntity<List<ApiKeyWithUserDto>> getAllApiKeys() {
    List<ApiKeyWithUserDto> apiKeys = apiKeyService.getAllApiKeys()
                                          .stream()
                                          .map(ApiKeyWithUserDto::from)
                                          .toList();
    return new ResponseEntity<>(apiKeys, HttpStatus.OK);
  }

  @CrossOrigin
  @PostMapping
  public ResponseEntity<ApiKeyWithUserDto>
  generateNewApiKey(@RequestBody ApiKeyRequest apiKeyRequest,
                    @AuthenticationPrincipal UserDetails userDetails) {
    ApiKeyWithUserDto apiKey =
        ApiKeyWithUserDto.from(apiKeyService.generateNewApiKey(
            apiKeyRequest.privilegeIds(), apiKeyRequest.expirationTime(), userDetails));
    return new ResponseEntity<>(apiKey, HttpStatus.CREATED);
  }

  private record ApiKeyRequest(
      @NotNull(message = "Privilege IDs cannot be null")
      @NotEmpty(message = "Privilege IDs cannot be empty")
      List<@NotNull(message = "Privilege ID cannot be null") Long> privilegeIds,
      @NotNull(message = "Expiration time cannot be null")
      @NotEmpty(message = "Expiration time cannot be empty")
      Long expirationTime) {}

  @Autowired
  public ApiKeyController(ApiKeyService apiKeyService) {
    this.apiKeyService = apiKeyService;
  }
}
