package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "module" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "starting_price")
    private double startingPrice;

    @Column(name = "nature")
    private String nature;

    @Column(name = "angle")
    private String angle;

    @Column(name = "unit_use")
    private String unitUse;

    @Column(name = "cctp")
    private String cctp;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "range")
    private Ranges range;
}
