package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.User;
import fr.madera.madera_backend.repositories.UserRepository;
import fr.madera.madera_backend.tools.Constante;
import fr.madera.madera_backend.tools.EmailService;
import fr.madera.madera_backend.tools.Mail;
import fr.madera.madera_backend.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

        private UserRepository userRepository;
        private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

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


    @PostMapping("/updpwd")
    public int updateMDPUserById(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userRepository.updateMDPUserById(user.getPassword(), user.getId());
    }

    @PostMapping("/upduserinfo")
    public int updateUserByEmail(@RequestBody User u){
        return this.userRepository.updateUser(u.getId(),u.getLastName(),u.getFirstName(),u.getPhone(),u.getMail());
    }

    @GetMapping("/resetmdpuser/{email}")
    public int resetMDPUserByEmail(@PathVariable String email) throws IOException, MessagingException {
        String mdp = Tools.GeneratePassword(8);
        String hash_mdp = bCryptPasswordEncoder.encode(mdp);
        if(this.userRepository.resetMDPUser(hash_mdp,email) == 1){
            Mail mail = new Mail();
            mail.setFrom(Constante.EMAIL);
            mail.setTo(email);
            mail.setSubject("Réinitialisation de votre mot de passe");

            Map<String, Object> model = new HashMap<String, Object>();
            model.put("mdp", mdp);
            model.put("tel", Constante.TEL);
            model.put("site", Constante.SITE);
            mail.setModel(model);

            emailService.sendHTMLMessage(mail, "request-new-password");

            return 1;
        }else{
            return 0;
        }
    }

    @PostMapping("/changeStateUser")
    public int changeStateUser(@RequestBody User user){
        return this.userRepository.changeStateUser(user.getId(), user.getIsActiv());
    }

}
