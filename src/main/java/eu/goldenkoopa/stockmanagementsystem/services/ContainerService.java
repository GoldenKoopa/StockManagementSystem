package eu.goldenkoopa.stockmanagementsystem.services;

import eu.goldenkoopa.stockmanagementsystem.data.Container;
import eu.goldenkoopa.stockmanagementsystem.data.dto.request.ContainerPostRequestDTO;
import eu.goldenkoopa.stockmanagementsystem.data.dto.response.ContainerDTO;
import eu.goldenkoopa.stockmanagementsystem.repositories.ContainerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ContainerService {

  private ContainerRepository containerRepository;

  public Container createContainer(ContainerPostRequestDTO details, String user) {
    if (this.containerRepository.existsByNameAndServer(details.name(), details.server())) {
      throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "container does already exist");
    }

    Container container = new Container(details.name(), user, details.data(), details.server());
    return this.containerRepository.save(container);
  }

  public Container getContainer(String name, String server) {
    return this.containerRepository.findByNameAndServer(name, server).orElse(null);
  }

  /**
   * Deletes a container from the repository.
   *
   * @param name The name of the container.
   * @param server The server of the container.
   */
  public void deleteContainer(String name, String server) {
    containerRepository.deleteByNameAndServer(name, server);
  }

  @Autowired
  public ContainerService(ContainerRepository containerRepository) {
    this.containerRepository = containerRepository;
  }

  public List<Container> getAllContainers() {
    return containerRepository.findAll();
  }
}
