package fr.madera.madera_backend.repositories;


import fr.madera.madera_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByMail(String mail);

}
