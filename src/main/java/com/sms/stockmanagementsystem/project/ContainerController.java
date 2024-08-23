package com.sms.stockmanagementsystem.project;


import com.sms.stockmanagementsystem.project.data.Container;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sms/api")
public class ContainerController {

    @Autowired
    private Security security;

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
    public Container setContainerDetails(@RequestBody String data, @RequestParam("secret") @NotNull String secret, @NotNull @RequestParam("server") String serverName) throws IOException, ParseException {
        security.checkSecret(secret);


        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(data);

        String name = (String) jsonObject.get("name");
        String user = (String) jsonObject.get("updatedBy");
        String containerData = (String) jsonObject.get("data");

        Container item;
        List<Container> containers = containerRepository.findByNameAndServer(name, serverName);
        if (containers.isEmpty()) {
            item = new Container(name, user, containerData, serverName);
        } else {
            item = containers.get(0);
            item.setData(containerData);
            item.setUpdatedBy(user);
            item.setUpdatedAt(LocalDateTime.now());
        }
        containerRepository.save(item);

        return item;
    }

    @CrossOrigin
    @GetMapping("/container")
    public Container getContainerDetails(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("containerId") String name, @NotNull @RequestParam("server") String server) throws IOException, ParseException {
        security.checkSecret(secret);
        List<Container> item = containerRepository.findByNameAndServer(name, server);
        if (item.isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }
        return item.get(0);
    }

    @CrossOrigin
    @DeleteMapping("/container")
    public String deleteContainerItem(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("containerId") String name, @NotNull @RequestParam("server") String server) {
        security.checkSecret(secret);
        List<Container> containerList = containerRepository.findByNameAndServer(name, server);
        if (containerList.isEmpty()) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "container does not exist");}
        Container container = containerList.get(0);
        for (Group group : container.getGroups()) {
            group.removeContainer(container);
            groupRepository.save(group);
        }
        containerRepository.delete(container);
        return "success";
    }

    @CrossOrigin
    @GetMapping("/getContainers")
    public List<Container> getAllContainers(@NotNull @RequestParam("secret") String secret, @RequestParam(value = "server", required = false) String server) {
        security.checkSecret(secret);
        return server != null ? containerRepository.findByServer(server) : containerRepository.findAll();
    }
}
