package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.User;
import fr.madera.madera_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    UserRepository userRepository;
    @Autowired
    UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @GetMapping("/find")
    public Iterable<User> findall(){
        return this.userRepository.findAll();
    }

}
