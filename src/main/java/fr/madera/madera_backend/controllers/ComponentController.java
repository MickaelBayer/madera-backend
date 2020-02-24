package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Component;
import fr.madera.madera_backend.repositories.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/component")
public class ComponentController
{
    private ComponentRepository componentRepository;

    @Autowired
    public ComponentController(ComponentRepository componentRepository)
    {
        this.componentRepository = componentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Component>> getComponents() {
        return new ResponseEntity<List<Component>>(
                (List<Component>)this.componentRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Long id) {
        Component component = this.componentRepository.findById(id).orElse(null);
        if (component != null) {
            return new ResponseEntity<Component>(component, HttpStatus.OK);
        } else {
            return new ResponseEntity<Component>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/projet/{idFilm}")
    public ResponseEntity<List<Component>> getComponentWithIdProjet(@PathVariable Long idProjet) {
        return new ResponseEntity<List<Component>>(
                (List<Component>)this.componentRepository.findWithIdProjet(idProjet),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Component> createComponent(@RequestBody Component component) {
        component = this.componentRepository.save(component);
        System.out.println("Component created : " + component.getId());
        return new ResponseEntity<Component>(component, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Component> updateComponent(@RequestBody Component component) {
        if ( this.componentRepository.existsById(component.getId()) ) {
            this.componentRepository.save(component);
            return new ResponseEntity<Component>(component, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        Component component = this.componentRepository.findById(id).orElse(null);
        if (component != null) {
            this.componentRepository.delete(component);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
