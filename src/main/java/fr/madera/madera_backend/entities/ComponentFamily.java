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
@Table(name = "component_family" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentFamily {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "family")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Component> components;
}
