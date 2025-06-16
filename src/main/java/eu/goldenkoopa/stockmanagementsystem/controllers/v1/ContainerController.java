package eu.goldenkoopa.stockmanagementsystem.controllers.v1;

import eu.goldenkoopa.stockmanagementsystem.data.Container;
import eu.goldenkoopa.stockmanagementsystem.data.Group;
import eu.goldenkoopa.stockmanagementsystem.data.dto.request.ContainerPostRequestDTO;
import eu.goldenkoopa.stockmanagementsystem.data.dto.response.ContainerDTO;
import eu.goldenkoopa.stockmanagementsystem.data.dto.response.GroupDTO;
import eu.goldenkoopa.stockmanagementsystem.services.ContainerService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/containers")
@EnableMethodSecurity(securedEnabled = true)
public class ContainerController {

  private final ContainerService containerService;

  @Secured({"API_HEALTH_READ"})
  @GetMapping("/health")
  public String health() {
    return "alive";
  }

  @GetMapping()
  public List<ContainerDTO> getAllContainers() {
    return containerService.getAllContainers().stream().map(ContainerDTO::from).toList();
  }

  @Secured({"WRITE_PRIVILEGE", "API_CONTAINER_CREATE"})
  @PostMapping()
  public ResponseEntity<ContainerDTO> setContainerDetails(
      @Valid @RequestBody ContainerPostRequestDTO containerDetails,
      @AuthenticationPrincipal UserDetails userDetails) {

    return new ResponseEntity<ContainerDTO>(
        ContainerDTO.from(
            this.containerService.createContainer(containerDetails, userDetails.getUsername())),
        HttpStatus.CREATED);
  }

  @Secured({"READ_PRIVILEGE", "API_CONTAINER_READ"})
  @GetMapping("/{id}")
  public ResponseEntity<ContainerDTO> getContainer(
      @PathVariable String id, @RequestParam("server") String server) {

    Container container = this.containerService.getContainer(id, server);
    if (container == null) {
      return ResponseEntity.notFound().build();
    }
    ContainerDTO from = ContainerDTO.from(container);
    return ResponseEntity.ok(from);
  }

  @Secured({"READ_PRIVILEGE", "API_CONTAINER_DELETE"})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteContainer(
      @PathVariable String id, @RequestParam("server") String server) {

    containerService.deleteContainer(id, server);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/groups")
  public List<GroupDTO> getContainerGroups(
      @PathVariable String id, @RequestParam("server") String server) {

    List<Group> groups = containerService.getContainerGroups(id, server);

    return groups.stream().map(GroupDTO::from).toList();
  }


  @Autowired
  public ContainerController(ContainerService containerService) {
    this.containerService = containerService;
  }
}
