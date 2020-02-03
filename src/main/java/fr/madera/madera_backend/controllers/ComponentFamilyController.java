package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.ComponentFamily;
import fr.madera.madera_backend.repositories.ComponentFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/componentFamily")
public class ComponentFamilyController
{
    private ComponentFamilyRepository componentFamilyRepository;

    @Autowired
    public ComponentFamilyController(ComponentFamilyRepository componentFamilyRepository)
    {
        this.componentFamilyRepository = componentFamilyRepository;
    }

    @GetMapping
    public ResponseEntity<List<ComponentFamily>> getComponentFamily() {
        return new ResponseEntity<List<ComponentFamily>>(
                (List<ComponentFamily>)this.componentFamilyRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentFamily> getComponentFamilyById(@PathVariable Long id) {
        ComponentFamily componentFamily = this.componentFamilyRepository.findById(id).orElse(null);
        if (componentFamily != null) {
            return new ResponseEntity<ComponentFamily>(componentFamily, HttpStatus.OK);
        } else {
            return new ResponseEntity<ComponentFamily>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<ComponentFamily> createComponentFamily(@RequestBody ComponentFamily componentFamily) {
        componentFamily = this.componentFamilyRepository.save(componentFamily);
        System.out.println("Component Family created : " + componentFamily.getId());
        return new ResponseEntity<ComponentFamily>(componentFamily, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ComponentFamily> updateComponentFamily(@RequestBody ComponentFamily componentFamily) {
        if ( this.componentFamilyRepository.existsById(componentFamily.getId()) ) {
            this.componentFamilyRepository.save(componentFamily);
            return new ResponseEntity<ComponentFamily>(componentFamily, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponentFamily(@PathVariable Long id) {
        ComponentFamily componentFamily = this.componentFamilyRepository.findById(id).orElse(null);
        if (componentFamily != null) {
            this.componentFamilyRepository.delete(componentFamily);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
