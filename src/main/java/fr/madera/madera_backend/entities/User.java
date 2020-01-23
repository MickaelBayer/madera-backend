package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @Column(name = "role")
    private Role role;

    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Project> projects;

}
