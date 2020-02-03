package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.User;
import fr.madera.madera_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

        private UserRepository userRepository;
        private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserRepository userRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<List<User>>(
                (List<User>)this.userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User personne = this.userRepository.findById(id).orElse(null);
        if (personne != null) {
            return new ResponseEntity<User>(personne, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/find")
    public Iterable<User> findall(){
        return this.userRepository.findAll();
    }

    @GetMapping("/updateUserId/{mail}")
    public ResponseEntity<User> getUserByIdWithMail(@PathVariable String mail) {
        User personne = this.userRepository.findUserByMail(mail);
        if (personne != null) {
            return new ResponseEntity<User>(personne, HttpStatus.OK);
        } else {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        if ( this.userRepository.existsById(user.getId()) ) {
            this.userRepository.save(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            this.userRepository.delete(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
