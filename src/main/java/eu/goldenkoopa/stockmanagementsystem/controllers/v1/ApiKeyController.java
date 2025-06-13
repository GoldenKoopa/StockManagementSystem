package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.dto.request.ApiKeyPostRequestDTO;
import eu.goldenkoopa.stockmanagementsystem.data.dto.response.authentication.ApiKeyWithUserDTO;
import eu.goldenkoopa.stockmanagementsystem.services.ApiKeyService;

import java.util.List;

import jakarta.validation.Valid;
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

/** ApiKeyController */
@RestController
@RequestMapping("/api/v1/apikeys")
@Secured({"ROLE_ADMIN"})
public class ApiKeyController {

  private final ApiKeyService apiKeyService;

  @GetMapping
  public ResponseEntity<List<ApiKeyWithUserDTO>> getAllApiKeys() {
    List<ApiKeyWithUserDTO> apiKeys =
        apiKeyService.getAllApiKeys().stream().map(ApiKeyWithUserDTO::from).toList();
    return new ResponseEntity<>(apiKeys, HttpStatus.OK);
  }

  @CrossOrigin
  @PostMapping
  public ResponseEntity<ApiKeyWithUserDTO> generateNewApiKey(
    @Valid @RequestBody ApiKeyPostRequestDTO apiKeyRequest, @AuthenticationPrincipal UserDetails userDetails) {
    ApiKeyWithUserDTO apiKey =
        ApiKeyWithUserDTO.from(
            apiKeyService.generateNewApiKey(
                apiKeyRequest.privilegeIds(), apiKeyRequest.expirationTime(), userDetails));
    return new ResponseEntity<>(apiKey, HttpStatus.CREATED);
  }

  @Autowired
  public ApiKeyController(ApiKeyService apiKeyService) {
    this.apiKeyService = apiKeyService;
  }
}
