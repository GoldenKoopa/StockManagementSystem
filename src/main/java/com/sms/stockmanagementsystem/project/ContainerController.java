package com.sms.stockmanagementsystem.project;


import com.sms.stockmanagementsystem.project.data.Container;
import com.sms.stockmanagementsystem.project.data.ContainerDTO;
import com.sms.stockmanagementsystem.project.data.Group;
import com.sms.stockmanagementsystem.project.repositories.ContainerRepository;
import com.sms.stockmanagementsystem.project.repositories.GroupRepository;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/sms/api")
public class ContainerController {

    @Autowired
    private ContainerRepository containerRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/health")
    public String health() {
        return "alive";
    }

    @CrossOrigin
    @PostMapping("/container")
    public ContainerDTO setContainerDetails(@RequestBody String data, @RequestParam("secret") @NotNull String secret) throws IOException, ParseException {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(data);

        Container item = new Container((String) jsonObject.get("id"), (String) jsonObject.get("user"), (String) jsonObject.get("data"));
        containerRepository.save(item);

        return new ContainerDTO(item);
    }

    @CrossOrigin
    @GetMapping("/container")
    public ContainerDTO getContainerDetails(@NotNull @RequestParam("secret") String secret, @RequestParam("id") String id) throws IOException, ParseException {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        Optional<Container> item = containerRepository.findById(id);
        if (item.isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }
        return new ContainerDTO(item.get());
    }

    @CrossOrigin
    @DeleteMapping("/container")
    public String deleteContainerItem(@NotNull @RequestParam("secret") String secret, @RequestParam("id") String id) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        Optional<Container> containerOptional = containerRepository.findById(id);
        if (containerOptional.isEmpty()) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "container does not exist");}
        Container container = containerOptional.get();
        for (Group group : container.getGroups()) {
            group.removeContainer(container);
            groupRepository.save(group);
        }
        containerRepository.delete(container);
        return "success";
    }
}
