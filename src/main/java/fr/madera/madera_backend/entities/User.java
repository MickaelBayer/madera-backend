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

    //@Column(name = "id", insertable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "id" )
    private Long id;

    @Column(name = "mail")
    private String mail;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "password")
    private String password;

    @JoinColumn(name = "role")
    @ManyToOne
    private Role role;

    @Column(name = "phone")
    private String phone;

    @Column(name = "firstconnection")
    private Boolean firstConnection;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Project> projects;

}
