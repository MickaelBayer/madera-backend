package fr.madera.madera_backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "quotation_state" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuotationState {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "state")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Quotation> quotations;
}
