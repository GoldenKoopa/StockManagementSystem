package eu.goldenkoopa.stockmanagementsystem.data.authentication;

import jakarta.persistence.*;
import java.util.Collection;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private boolean enabled;
  private boolean tokenExpired;

  @OneToMany(mappedBy = "user") private Collection<ApiKey> apiKeys;

  @ManyToMany
  @JoinTable(name = "users_roles",
             joinColumns =
                 @JoinColumn(name = "user_id", referencedColumnName = "id"),
             inverseJoinColumns =
                 @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Collection<Role> roles;

  @Override
  public String toString() {
    return id + username + firstName + lastName;
  }
}
