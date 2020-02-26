package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity(name = "cctp")
@Table(name = "cctp" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cctp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

 /*   @OneToMany(mappedBy = "role")
    private List<User> users;

  */
}
