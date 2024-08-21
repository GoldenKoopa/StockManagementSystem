package com.sms.stockmanagementsystem.project.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Entity
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

    public Container() {}

    public Container(String name, LocalDateTime time, String username, String data) {
        this.name = name;
        this.updatedAt = time;
        this.updatedBy = username;
        this.data = data;
    }

    public Container(String name, String user, String data, String server) {
        this.server = server;
        this.name = name;
        this.updatedBy = user;
        this.data = data;
        this.updatedAt = LocalDateTime.now();
        this.createdAt= LocalDateTime.now();
        this.createdBy = user;
    }

}
