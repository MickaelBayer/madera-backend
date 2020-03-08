package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.ProjectModule;
import fr.madera.madera_backend.entities.Quotation;
import fr.madera.madera_backend.repositories.ProjectModuleRepository;
import fr.madera.madera_backend.repositories.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectModule")
public class ProjectModuleController
{
    private ProjectModuleRepository projectModuleRepository;
    private QuotationRepository quotationRepository;

    @Autowired
    public ProjectModuleController(ProjectModuleRepository projectModuleRepository,
                                   QuotationRepository quotationRepository)
    {
        this.projectModuleRepository = projectModuleRepository;
        this.quotationRepository = quotationRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProjectModule>> getProjectModule() {
        return new ResponseEntity<List<ProjectModule>>(
                (List<ProjectModule>)this.projectModuleRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectModule> getProjectModuleById(@PathVariable Long id) {
        ProjectModule projectModule = this.projectModuleRepository.findById(id).orElse(null);
        if (projectModule != null) {
            return new ResponseEntity<ProjectModule>(projectModule, HttpStatus.OK);
        } else {
            return new ResponseEntity<ProjectModule>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<ProjectModule>> getByProject(@PathVariable Long id) {
        return new ResponseEntity<List<ProjectModule>>(
                (List<ProjectModule>)this.projectModuleRepository.findByProject(id),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectModule> createProjectModule(@RequestBody ProjectModule projectModule) {
        projectModule = this.projectModuleRepository.save(projectModule);
        System.out.println("ProjectModule created : " + projectModule.getId());
        return new ResponseEntity<ProjectModule>(projectModule, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<ProjectModule> updateProjectModule(@RequestBody ProjectModule projectModule) {
        if ( this.projectModuleRepository.existsById(projectModule.getId()) ) {
            this.projectModuleRepository.save(projectModule);
            return new ResponseEntity<ProjectModule>(projectModule, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectModule(@PathVariable Long id) {
        ProjectModule projectModule = this.projectModuleRepository.findById(id).orElse(null);
        if (projectModule != null) {
            this.projectModuleRepository.delete(projectModule);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
