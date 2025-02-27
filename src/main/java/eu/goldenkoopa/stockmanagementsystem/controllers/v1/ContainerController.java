package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.Container;
import eu.goldenkoopa.stockmanagementsystem.data.Group;
import eu.goldenkoopa.stockmanagementsystem.repositories.ContainerRepository;
import eu.goldenkoopa.stockmanagementsystem.repositories.GroupRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@RequestMapping("/api/v1")
@EnableMethodSecurity(securedEnabled = true)
public class ContainerController {

  private final ContainerRepository containerRepository;

  private final GroupRepository groupRepository;

  @Secured({ "WRITE_PRIVILEGE" })
  @GetMapping("/health")
  public String health() {
    return "alive";
  }

  @CrossOrigin
  @Secured({"WRITE_PRIVILEGE", "API_CONTAINER_CREATE"})
  @PostMapping("/container")
  public Container setContainerDetails(@RequestBody String data,
      @NotNull @RequestParam("server") String serverName)
      throws ParseException {

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
  public Container getContainerDetails(@NotNull @RequestParam("containerId") String name,
      @NotNull @RequestParam("server") String server) {
    List<Container> item = containerRepository.findByNameAndServer(name, server);
    if (item.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
    }
    return item.get(0);
  }

  @CrossOrigin
  @DeleteMapping("/container")
  public String deleteContainerItem(@NotNull @RequestParam("containerId") String name,
      @NotNull @RequestParam("server") String server) {
    List<Container> containerList = containerRepository.findByNameAndServer(name, server);
    if (containerList.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
          "container does not exist");
    }
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
  public List<Container> getAllContainers(
      @RequestParam(value = "server", required = false) String server) {
    return server != null ? containerRepository.findByServer(server)
        : containerRepository.findAll();
  }

  @GetMapping("/getContainerGroups")
  public List<Group> getContainerGroups(@RequestParam("server") String server,
      @RequestParam("containerId") String name) {
    List<Container> containerList = containerRepository.findByNameAndServer(name, server);
    if (containerList.isEmpty()) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST,
          "container does not exist");
    }
    Container container = containerList.get(0);
    return container.getGroups();
  }

  @Autowired
  public ContainerController(ContainerRepository containerRepository,
      GroupRepository groupRepository) {
    this.containerRepository = containerRepository;
    this.groupRepository = groupRepository;
  }
}
