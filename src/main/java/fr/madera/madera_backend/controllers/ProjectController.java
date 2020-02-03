package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Project;
import fr.madera.madera_backend.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController
{
    private ProjectRepository projectRepository;

    @Autowired
    public ProjectController(ProjectRepository projectRepository)
    {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProject() {
        return new ResponseEntity<List<Project>>(
                (List<Project>)this.projectRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = this.projectRepository.findById(id).orElse(null);
        if (project != null) {
            return new ResponseEntity<Project>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<Project>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        project = this.projectRepository.save(project);
        System.out.println("Component Family created : " + project.getId());
        return new ResponseEntity<Project>(project, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Project> updateProject(@RequestBody Project project) {
        if ( this.projectRepository.existsById(project.getId()) ) {
            this.projectRepository.save(project);
            return new ResponseEntity<Project>(project, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Project project = this.projectRepository.findById(id).orElse(null);
        if (project != null) {
            this.projectRepository.delete(project);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
