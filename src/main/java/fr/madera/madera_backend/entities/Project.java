package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "project" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "user")
    @ManyToOne
    private User user;

    @JoinColumn(name = "customer")
    @ManyToOne
    private Customer customer;
}
