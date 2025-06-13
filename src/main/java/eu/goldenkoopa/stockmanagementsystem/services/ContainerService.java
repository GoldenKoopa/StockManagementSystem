package eu.goldenkoopa.stockmanagementsystem.services;

import eu.goldenkoopa.stockmanagementsystem.data.Container;
import eu.goldenkoopa.stockmanagementsystem.data.dto.request.ContainerPostRequestDTO;
import eu.goldenkoopa.stockmanagementsystem.repositories.ContainerRepository;
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

  @Autowired
  public ContainerService(ContainerRepository containerRepository) {
    this.containerRepository = containerRepository;
  }
}
