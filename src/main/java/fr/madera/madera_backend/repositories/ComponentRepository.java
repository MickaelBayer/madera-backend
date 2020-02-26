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

    @Query(value = "SELECT * FROM component c \n" +
                    "\tINNER JOIN component_family ON c.family = component_family.id \n" +
                    "\tINNER JOIN modfam_compofam ON component_family.id = modfam_compofam.component_family_id \n" +
                    "WHERE modfam_compofam.module_family_id = ?1 \n" +
                    "\tAND c.ranges = ?2 ;", nativeQuery = true)
    public List<Component> findWithFamiliesAndRange(Long moduleFamilyId, Long rangeId);

    @Query(value = "SELECT * FROM component c \n" +
            "\tINNER JOIN module_component ON c.id = module_component.component_id \n" +
            "WHERE module_component.module_id = ?1 ;", nativeQuery = true)
    public List<Component> findByModuleId(Long module_id);
}
