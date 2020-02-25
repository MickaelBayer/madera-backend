package fr.madera.madera_backend.repositories;

import fr.madera.madera_backend.entities.Component;
import fr.madera.madera_backend.entities.ComponentFamily;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//l'interface Crudrepository implémente les methodes CRUD classiques
//CrudRepository<Type de l'entité, type de l'ID>
@Repository
public interface ComponentFamilyRepository extends CrudRepository<ComponentFamily, Long>
{
    @Query(value = "SELECT * FROM component_family \n" +
                    "\tINNER JOIN modfam_compofam ON modfam_compofam.component_family_id = component_family.id \n" +
                    "WHERE modfam_compofam.module_family_id = ?1 ;", nativeQuery = true)
    public List<ComponentFamily> findWithModuleFamily(Long moduleFamilyId);

}
