package fr.madera.madera_backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.internal.build.AllowSysOut;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "module" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "starting_price")
    private double startingPrice;

    @Column(name = "specs")
    private String specs;

    @ManyToOne
    @JoinColumn(name = "cctp")
    private Cctp cctp;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "range")
    private Ranges ranges;

    @ManyToOne
    @JoinColumn(name = "family")
    private ModuleFamily family;

    @JsonBackReference
    @OneToMany
    @JoinTable(name = "module_component",
               joinColumns = {@JoinColumn(name = "module_id")},
               inverseJoinColumns = {@JoinColumn(name = "component_id")})
    private List<Component> components;
}
