package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "component" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Component {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "unit_use")
    private String unitUse;

    @Column(name = "nature")
    private int nature;

    @Column(name = "default_quantity")
    private double defaultQuantity;

    @ManyToOne
    @JoinColumn(name = "family")
    private ComponentFamily family;
}
