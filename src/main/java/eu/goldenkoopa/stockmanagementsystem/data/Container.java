package eu.goldenkoopa.stockmanagementsystem.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "Container")
public class Container {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String createdBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String updatedBy;

  @Column(columnDefinition = "TEXT")
  private String data;

  @JsonIgnore
  @ManyToMany(mappedBy = "containers")
  private List<Group> groups;

  private String server;

  @Column(columnDefinition = "TEXT")
  private String notes;

  public Container() {}

  public Container(String name, String user, String data, String server) {
    this.server = server;
    this.name = name;
    this.updatedBy = user;
    this.data = data;
    this.updatedAt = LocalDateTime.now();
    this.createdAt = LocalDateTime.now();
    this.createdBy = user;
    this.groups = new ArrayList<>();
    this.notes = "";
  }

  public void removeGroup(Group group) {
    this.groups.remove(group);
  }
}
