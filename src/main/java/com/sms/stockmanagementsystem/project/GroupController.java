package com.sms.stockmanagementsystem.project;

import com.sms.stockmanagementsystem.project.data.Container;
import com.sms.stockmanagementsystem.project.data.ContainerDTO;
import com.sms.stockmanagementsystem.project.data.Group;
import com.sms.stockmanagementsystem.project.repositories.ContainerRepository;
import com.sms.stockmanagementsystem.project.repositories.GroupRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sms/api")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ContainerRepository containerRepository;

    @PostMapping("/createGroup")
    public String createGroup(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("name") String name) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        if (!groupRepository.findByName(name).isEmpty()) {throw new ResponseStatusException(HttpStatus.CONFLICT, "group already exists");}
        Group group = new Group(name);
        groupRepository.save(group);
        return "success";
    }

    @PostMapping("/addToGroup")
    public String addToGroup(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("name") String name, @NotNull @RequestParam("id") String id) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        Group group = groupRepository.findByName(name).get(0);
        if (group == null) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");}
        Optional<Container> containerOptional = containerRepository.findById(id);
        if (containerOptional.isEmpty()) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "id does not exist");}
        Container container = containerOptional.get();
        group.addContainer(container);
        groupRepository.save(group);
        return "success";
    }

    @GetMapping("/getGroup")
    public List<ContainerDTO> getGroup(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("name") String name) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        List<Group> groups = groupRepository.findByName(name);
        if (groups.isEmpty()) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");}
        return groups.get(0).getContainers().stream().map(ContainerDTO::new).toList();
    }

    @DeleteMapping("/deleteGroup")
    public String deleteGroup(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("name") String name) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        Group group = groupRepository.findByName(name).get(0);
        if (group == null) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");}
        groupRepository.delete(group);
        return "success";
    }

    @GetMapping("/groups")
    public List<Group> getAllGroups(@NotNull @RequestParam("secret") String secret) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        return groupRepository.findAll();
    }

    @PostMapping("/renameGroup")
    public String renameGroup(@NotNull @RequestParam("secret") String secret, @NotNull @RequestParam("name") String name, @NotNull @RequestParam("renameTo") String renameString) {
        if (!secret.equals("25e60f5880facc11bebbfc922c028f57")) {throw new HttpServerErrorException(HttpStatus.FORBIDDEN);}
        Group group = groupRepository.findByName(name).get(0);
        if (group == null) {throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");}
        group.setName(renameString);
        groupRepository.save(group);
        return "success";
    }
}
