package fr.madera.madera_backend.repositories;


import fr.madera.madera_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findUserByMail(String mail);

    // modification du mot de passe via ID
    @Modifying
    @Transactional
    @Query(value = "UPDATE User SET password=?1 WHERE id =?2")
    int updateMDPUserById(String password, Long id);

    // modification de toutes les infos sur un utilisateur hors mis le mot de passe
    @Modifying
    @Transactional
    @Query(value = "UPDATE User SET lastName=?2, firstName=?3, phone=?4, mail=?5 WHERE id =?1")
    int updateUser(Long id, String lastName, String firstName, String phone, String mail);

    //Réinitialisation MDP user
    @Modifying
    @Transactional
    @Query(value = "UPDATE User SET password=?1 WHERE mail =?2")
    int resetMDPUser(String password, String email);
}
