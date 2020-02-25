package fr.madera.madera_backend.repositories;

import fr.madera.madera_backend.entities.Component;
import fr.madera.madera_backend.entities.ModuleFamily;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//l'interface Crudrepository implémente les methodes CRUD classiques
//CrudRepository<Type de l'entité, type de l'ID>
@Repository
public interface ModuleFamilyRepository extends CrudRepository<ModuleFamily, Long>
{

}