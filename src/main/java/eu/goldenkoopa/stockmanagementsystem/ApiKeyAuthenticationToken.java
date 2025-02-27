package eu.goldenkoopa.stockmanagementsystem;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * ApiKeyAuthenticationToken
 */
public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

  private ApiKey apiKey;

  public ApiKeyAuthenticationToken(ApiKey apiKey) {
    super(AuthorityUtils.createAuthorityList(
        apiKey.getPrivileges()
            .stream()
            .map(privilege -> privilege.getName())
            .toList()));
    this.apiKey = apiKey;
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return apiKey;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    throw new RuntimeException("cannot modify");
  }
}
