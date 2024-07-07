package com.sms.stockmanagementsystem.project;


import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;

@RestController
@RequestMapping("/sms/api")
public class Controller {

    @GetMapping("/health")
    public String health() {
        return "alive";
    }

    @CrossOrigin
    @PostMapping("/container")
    public ContainerItem setContainerDetails(@RequestBody String data, @RequestParam("secret") @NotNull String secret, @RequestParam("server") String server) throws IOException, ParseException {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(data);

        ContainerItem item = new ContainerItem((String) jsonObject.get("id"), (String) jsonObject.get("user"), (String) jsonObject.get("data"));



        JSONConverter.save(item, server);
        return item;
    }

    @CrossOrigin
    @GetMapping("/container")
    public ContainerItem getContainerDetails(@RequestParam("server") String server, @NotNull @RequestParam("secret") String secret, @RequestParam("id") String id) throws IOException, ParseException {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        return JSONConverter.getContainerItems(server, id);
    }
}
