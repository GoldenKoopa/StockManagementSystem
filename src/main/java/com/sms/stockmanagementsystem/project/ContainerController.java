package com.sms.stockmanagementsystem.project;


import com.sms.stockmanagementsystem.project.data.Container;
import com.sms.stockmanagementsystem.project.data.Group;
import com.sms.stockmanagementsystem.project.multiTenant.TenantIdentifierResolver;
import com.sms.stockmanagementsystem.project.repositories.ContainerRepository;
import com.sms.stockmanagementsystem.project.repositories.GroupRepository;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sms/api")
public class ContainerController {

    @Autowired
    private TenantIdentifierResolver tenantIdentifierResolver;



    @Autowired
    TransactionTemplate transactionTemplate;

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
    public Container setContainerDetails(@RequestBody String data, @RequestParam("secret") @NotNull String secret, @NotNull @RequestParam("server") String serverName) throws ParseException {
        security.checkSecret(secret);

        tenantIdentifierResolver.setCurrentTenant(serverName);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(data);

        String name = (String) jsonObject.get("name");
        String user = (String) jsonObject.get("updatedBy");
        String containerData = (String) jsonObject.get("data");

        Container item;
        List<Container> containers = containerRepository.findByName(name);
        if (containers.isEmpty()) {
            item = new Container(name, user, containerData);
        } else {
            item = containers.get(0);
            item.setData(containerData);
            item.setUpdatedBy(user);
            item.setUpdatedAt(LocalDateTime.now());
        }

//        transactionTemplate.execute(tx -> containerRepository.save(item));
        containerRepository.save(item);

        return item;
    }

    @CrossOrigin
    @GetMapping("/container")
    public Container getContainerDetails(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("containerId") String name, @NotNull @RequestParam("server") String serverName) {
        security.checkSecret(secret);
//        tenantIdentifierResolver.setCurrentTenant(serverName);

        List<Container> item = containerRepository.findByName(name);
        if (item.isEmpty()) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }
        return item.get(0);
    }

    @CrossOrigin
    @DeleteMapping("/container")
    public String deleteContainerItem(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("containerId") String name, @NotNull @RequestParam("server") String serverName) {
        security.checkSecret(secret);
//        tenantIdentifierResolver.setCurrentTenant(serverName);

        List<Container> containerList = containerRepository.findByName(name);
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
    public List<Container> getAllContainers(@NotNull @RequestParam("secret") String secret, @RequestParam(value = "server", required = false) String serverName) {
        security.checkSecret(secret);
//        tenantIdentifierResolver.setCurrentTenant(serverName);

        return containerRepository.findAll();
    }

    @GetMapping("/getContainerGroups")
    public List<Group> getContainerGroups(@RequestParam("secret") String secret, @RequestParam("server") String server, @RequestParam("containerId") String name, @RequestParam("server") String serverName) {
        security.checkSecret(secret);
//        tenantIdentifierResolver.setCurrentTenant(serverName);

        List<Container> containerList = containerRepository.findByName(name);
        if (containerList.isEmpty()) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "container does not exist");}
        Container container = containerList.get(0);
        return container.getGroups();
    }
}
