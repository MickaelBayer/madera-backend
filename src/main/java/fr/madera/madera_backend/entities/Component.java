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

    @Column(name = "specs")
    private String specs;

    @ManyToOne
    @JoinColumn(name = "provider")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "ranges")
    private Ranges ranges;

    @ManyToOne
    @JoinColumn(name = "family")
    private ComponentFamily family;
}
