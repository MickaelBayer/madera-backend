package fr.madera.madera_backend.repositories;

import fr.madera.madera_backend.entities.Component;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//l'interface Crudrepository implémente les methodes CRUD classiques
//CrudRepository<Type de l'entité, type de l'ID>
@Repository
public interface ComponentRepository extends CrudRepository<Component, Long>
{
    // !!!!!! QUERY à FAIRE !!!!
    @Query(value = "SELECT * FROM component c INNER JOIN plansalle p ON c.idcinema = p.idcinema INNER JOIN seance s ON p.idplanSalle = s.idplanSalle WHERE s.idfilm = ?1 GROUP BY c.id", nativeQuery = true)
    public List<Component> findWithIdProjet (Long idProjet);
}
