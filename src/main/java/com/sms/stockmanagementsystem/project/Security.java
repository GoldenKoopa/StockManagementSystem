package com.sms.stockmanagementsystem.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class Security {

  Environment environment;

  public void checkSecret(String secret) {
    if (!secret.equals(environment.getProperty("secret"))) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
  }


  @Autowired
  public Security(Environment environment) {
    this.environment = environment;
  }
}
