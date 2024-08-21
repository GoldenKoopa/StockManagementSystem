package com.sms.stockmanagementsystem.project.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime createdAt;
    private String createdBy;
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "group_containers",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "container_id")
    )
    private List<Container> containers;

    public Group() {}

    public Group(String name, String user) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.createdBy = user;
    }

    public List<Container> getContainers(String server) {
        return this.containers.stream().filter(container -> container.getServer().equalsIgnoreCase(server)).toList();
    }

    public void addContainer(Container container) {
        this.containers.add(container);
    }

    public void removeContainer(Container container) {
        this.containers.remove(container);
    }

}
