package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Module;
import fr.madera.madera_backend.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/module")
public class ModuleController
{
    private ModuleRepository moduleRepository;

    @Autowired
    public ModuleController(ModuleRepository moduleRepository)
    {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Module>> getModule() {
        return new ResponseEntity<List<Module>>(
                (List<Module>)this.moduleRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getModuleById(@PathVariable Long id) {
        Module module = this.moduleRepository.findById(id).orElse(null);
        if (module != null) {
            return new ResponseEntity<Module>(module, HttpStatus.OK);
        } else {
            return new ResponseEntity<Module>(HttpStatus.NO_CONTENT);
        }
    }


    @PostMapping
    public ResponseEntity<Module> createModule(@RequestBody Module module) {
        module = this.moduleRepository.save(module);
        System.out.println("Module created : " + module.getId());
        return new ResponseEntity<Module>(module, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Module> updateModule(@RequestBody Module module) {
        if ( this.moduleRepository.existsById(module.getId()) ) {
            this.moduleRepository.save(module);
            return new ResponseEntity<Module>(module, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(@PathVariable Long id) {
        Module module = this.moduleRepository.findById(id).orElse(null);
        if (module != null) {
            this.moduleRepository.delete(module);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
