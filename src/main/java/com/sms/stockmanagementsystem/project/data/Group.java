package com.sms.stockmanagementsystem.project.data;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    public Group(String name) {
        this.name = name;
    }

    public void addContainer(Container container) {
        this.containers.add(container);
    }

    public void removeContainer(Container container) {
        this.containers.remove(container);
    }

}
