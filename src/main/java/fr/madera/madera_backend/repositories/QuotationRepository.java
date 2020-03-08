package fr.madera.madera_backend.repositories;

import fr.madera.madera_backend.entities.ProjectModule;
import fr.madera.madera_backend.entities.Quotation;
import fr.madera.madera_backend.entities.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//l'interface Crudrepository implémente les methodes CRUD classiques
//CrudRepository<Type de l'entité, type de l'ID>
@Repository
public interface QuotationRepository  extends CrudRepository<Quotation, Long>
{
    @Query(value = "SELECT * FROM quotation \n" +
            "WHERE quotation.project = ?1 ;", nativeQuery = true)
    public List<Quotation> findByProject(Long projectID);
}