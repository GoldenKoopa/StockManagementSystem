package com.sms.stockmanagementsystem.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class Security {

    @Autowired
    Environment environment;


    public void checkSecret(String secret) {
        if (!secret.equals(environment.getProperty("secret"))) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
    }
}
