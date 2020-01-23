package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "equipment" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coupe_principe")
    private String coupePrincipe;

    @ManyToOne
    @JoinColumn(name = "provider")
    private Provider provider;
}
