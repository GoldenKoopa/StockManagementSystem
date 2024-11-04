package com.sms.stockmanagementsystem.project;

import com.sms.stockmanagementsystem.project.data.Container;
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

  @Autowired private Security security;

  @Autowired private GroupRepository groupRepository;

  @Autowired private ContainerRepository containerRepository;

  @PostMapping("/createGroup")
  public String createGroup(
      @NotNull @RequestParam("secret") String secret,
      @NotNull @RequestParam("name") String name,
      @RequestParam("user") String user) {
    security.checkSecret(secret);
    if (!groupRepository.findByName(name).isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "group already exists");
    }
    Group group = new Group(name, user);
    groupRepository.save(group);
    return "success";
  }

  @PostMapping("/addToGroup")
  public String addToGroup(
      @NotNull @RequestParam("secret") String secret,
      @NotNull @RequestParam("groupId") Integer groupId,
      @NotNull @RequestParam("containerId") String containerId,
      @NotNull @RequestParam("server") String server) {
    security.checkSecret(secret);
    Optional<Group> groupOptional = groupRepository.findById(groupId);
    if (groupOptional.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");
    }
    Group group = groupOptional.get();
    List<Container> containerOptional =
        containerRepository.findByNameAndServer(containerId, server);
    if (containerOptional.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "id does not exist");
    }
    Container container = containerOptional.get(0);
    group.addContainer(container);
    groupRepository.save(group);
    return "success";
  }

  @GetMapping("/getGroup")
  public List<Container> getGroup(
      @NotNull @RequestParam("secret") String secret,
      @NotNull @RequestParam("groupId") Integer groupId,
      @RequestParam("server") String server) {
    security.checkSecret(secret);
    Optional<Group> groups = groupRepository.findById(groupId);
    if (groups.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");
    }
    return groups.get().getContainers(server);
  }

  @DeleteMapping("/deleteGroup")
  public String deleteGroup(
      @NotNull @RequestParam("secret") String secret,
      @NotNull @RequestParam("groupId") Integer groupId) {
    security.checkSecret(secret);
    Optional<Group> groupOptional = groupRepository.findById(groupId);
    if (groupOptional.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");
    }
    groupRepository.delete(groupOptional.get());
    return "success";
  }

  @GetMapping("/groups")
  public List<Group> getAllGroups(@NotNull @RequestParam("secret") String secret) {
    security.checkSecret(secret);
    return groupRepository.findAll();
  }

  @PostMapping("/renameGroup")
  public String renameGroup(
      @NotNull @RequestParam("secret") String secret,
      @NotNull @RequestParam("groupId") Integer groupId,
      @NotNull @RequestParam("renameTo") String renameString) {
    security.checkSecret(secret);
    Optional<Group> groupOptional = groupRepository.findById(groupId);
    if (groupOptional.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");
    }
    groupOptional.get().setName(renameString);
    groupRepository.save(groupOptional.get());
    return "success";
  }

  @DeleteMapping("deleteFromGroup")
  public String deleteFromGroup(
      @RequestParam("secret") String secret,
      @RequestParam("groupId") Integer groupId,
      @RequestParam("containerId") String name,
      @RequestParam("server") String server) {
    security.checkSecret(secret);
    Optional<Group> groupOptional = groupRepository.findById(groupId);
    if (groupOptional.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "group does not exist");
    }
    List<Container> containerOptional = containerRepository.findByNameAndServer(name, server);
    if (containerOptional.isEmpty()) {
      throw new HttpServerErrorException(
          HttpStatus.BAD_REQUEST, "containerid does not exist (on this server?)");
    }
    Group group = groupOptional.get();
    Container container = containerOptional.get(0);
    if (!group.getContainers().contains(container)) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "container is not in group");
    }
    group.removeContainer(container);
    groupRepository.save(group);
    container.removeGroup(group);
    containerRepository.save(container);
    return "success";
  }
}
