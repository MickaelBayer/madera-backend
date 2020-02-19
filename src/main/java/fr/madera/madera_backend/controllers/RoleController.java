package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Role;
import fr.madera.madera_backend.entities.User;
import fr.madera.madera_backend.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController
{
    private RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public ResponseEntity<List<Role>> getRole() {
        return new ResponseEntity<List<Role>>(
                (List<Role>)this.roleRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/find")
    public Iterable<Role> findall(){
        return this.roleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Role role = this.roleRepository.findById(id).orElse(null);
        if (role != null) {
            return new ResponseEntity<Role>(role, HttpStatus.OK);
        } else {
            return new ResponseEntity<Role>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        role = this.roleRepository.save(role);
        System.out.println("Component Family created : " + role.getId());
        return new ResponseEntity<Role>(role, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        if ( this.roleRepository.existsById(role.getId()) ) {
            this.roleRepository.save(role);
            return new ResponseEntity<Role>(role, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Role role = this.roleRepository.findById(id).orElse(null);
        if (role != null) {
            this.roleRepository.delete(role);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
