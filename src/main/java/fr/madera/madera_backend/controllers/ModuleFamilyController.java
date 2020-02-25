package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.ModuleFamily;
import fr.madera.madera_backend.repositories.ModuleFamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moduleFamily")
public class ModuleFamilyController
{
    private ModuleFamilyRepository moduleFamilyRepository;

    @Autowired
    public ModuleFamilyController(ModuleFamilyRepository moduleFamilyRepository)
    {
        this.moduleFamilyRepository = moduleFamilyRepository;
    }

    @GetMapping
    public ResponseEntity<List<ModuleFamily>> getModuleFamily() {
        return new ResponseEntity<List<ModuleFamily>>(
                (List<ModuleFamily>)this.moduleFamilyRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModuleFamily> getModuleFamilyById(@PathVariable Long id) {
        ModuleFamily moduleFamily = this.moduleFamilyRepository.findById(id).orElse(null);
        if (moduleFamily != null) {
            return new ResponseEntity<ModuleFamily>(moduleFamily, HttpStatus.OK);
        } else {
            return new ResponseEntity<ModuleFamily>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<ModuleFamily> createModuleFamily(@RequestBody ModuleFamily moduleFamily) {
        moduleFamily = this.moduleFamilyRepository.save(moduleFamily);
        System.out.println("Component Family created : " + moduleFamily.getId());
        return new ResponseEntity<ModuleFamily>(moduleFamily, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ModuleFamily> updateModuleFamily(@RequestBody ModuleFamily moduleFamily) {
        if ( this.moduleFamilyRepository.existsById(moduleFamily.getId()) ) {
            this.moduleFamilyRepository.save(moduleFamily);
            return new ResponseEntity<ModuleFamily>(moduleFamily, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModuleFamily(@PathVariable Long id) {
        ModuleFamily moduleFamily = this.moduleFamilyRepository.findById(id).orElse(null);
        if (moduleFamily != null) {
            this.moduleFamilyRepository.delete(moduleFamily);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
