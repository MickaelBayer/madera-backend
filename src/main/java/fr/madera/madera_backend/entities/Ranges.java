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
@Table(name = "ranges" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ranges {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "percentage_final_price")
    private double percentageFinalPrice;

    @OneToMany(mappedBy = "ranges")
    @JsonBackReference(value = "component-range-back-reference")
    private List<Component> components;

    @OneToMany(mappedBy = "ranges")
    @JsonBackReference(value = "project-range-back-reference")
    private List<Project> projects;

    @OneToMany(mappedBy = "ranges")
    @JsonBackReference(value = "module-range-back-reference")
    private List<Module> modules;
}
