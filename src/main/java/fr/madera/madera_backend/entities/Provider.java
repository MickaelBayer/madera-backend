package fr.madera.madera_backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "provider" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "mail")
    private String mail;

    @OneToMany(mappedBy = "provider")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Equipment> equipments;
}
