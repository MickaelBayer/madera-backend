package fr.madera.madera_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "ranges" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ranges {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "percentage_final_price")
    private double percentageFinalPrice;
}
