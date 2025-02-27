package eu.goldenkoopa.stockmanagementsystem.data.authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Entity
@Data
public class ApiKey {

  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) private Long id;

  private String key;

  @ManyToOne private User user;

  private LocalDate expirationDate;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "apikeys_privileges",
             joinColumns =
                 @JoinColumn(name = "apikey_id", referencedColumnName = "id"),
             inverseJoinColumns = @JoinColumn(name = "privilege_id",
                                              referencedColumnName = "id"))
  private List<Privilege> privileges;
}
