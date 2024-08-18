package com.sms.stockmanagementsystem.project.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    private String id;

    private LocalDateTime time;
    private String username;
    private String data;

    @ManyToMany(mappedBy = "containers")
    private List<Group> groups;

    public Container() {}

    public Container(String id, LocalDateTime time, String username, String data) {
        this.id = id;
        this.time = time;
        this.username = username;
        this.data = data;
    }

    public Container(String id, String user, String data) {
        this.id = id;
        this.username = user;
        this.data = data;
        this.time = LocalDateTime.now();
    }

}
