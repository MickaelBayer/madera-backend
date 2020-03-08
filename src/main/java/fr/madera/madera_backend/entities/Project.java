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
@Table(name = "project" , schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @Column(name = "id", insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @ManyToOne
    @JoinColumn(name = "commercial")
    private User commercial;

    @JoinColumn(name = "customer")
    @ManyToOne
    private Customer customer;

    @JoinColumn(name = "range")
    @ManyToOne
    private Ranges ranges;

    @OneToMany(mappedBy = "project")
    @JsonBackReference(value = "project-projectModules-back-reference")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<ProjectModule> projectModules;

    @OneToMany(mappedBy = "project")
    @JsonBackReference(value = "project-quotation-back-reference")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Quotation> quotations;
}
