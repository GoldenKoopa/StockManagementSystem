package eu.goldenkoopa.stockmanagementsystem;

import eu.goldenkoopa.stockmanagementsystem.data.authentication.ApiKey;
import eu.goldenkoopa.stockmanagementsystem.services.ApiKeyService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeyFilter extends OncePerRequestFilter {

  private ApiKeyService apiKeyService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException {
    Map<String, String[]> pathVariables = request.getParameterMap();
    if (!pathVariables.keySet().contains("apiKey")) {
      filterChain.doFilter(request, response);
      return;
    }

    ApiKey apiKey;
    try {
      apiKey = apiKeyService.getApiKeyByKey(pathVariables.get("apiKey")[0]);
    } catch (Exception e) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.getWriter().write("api key not valid");
      return;
    }

    ApiKeyAuthenticationToken auth = new ApiKeyAuthenticationToken(apiKey);
    SecurityContext newContext = SecurityContextHolder.createEmptyContext();
    newContext.setAuthentication(auth);
    SecurityContextHolder.setContext(newContext);

    filterChain.doFilter(request, response);
  }

  public ApiKeyFilter(ApiKeyService apiKeyService) {
    this.apiKeyService = apiKeyService;
  }
}
