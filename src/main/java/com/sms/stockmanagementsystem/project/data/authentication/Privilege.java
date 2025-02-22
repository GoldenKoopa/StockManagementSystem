package com.sms.stockmanagementsystem.project.data.authentication;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "privileges")
@NoArgsConstructor
public class Privilege {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

  private String name;

  @ManyToMany(mappedBy = "privileges") private Collection<Role> roles;

  public Privilege(String name) { this.name = name; }
}
