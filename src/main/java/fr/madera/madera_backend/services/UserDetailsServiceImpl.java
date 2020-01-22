package fr.cesi.poec.services;

import fr.cesi.poec.entities.Personne;
import fr.cesi.poec.repositories.PersonneRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private PersonneRepository personneRepository;

    public UserDetailsServiceImpl(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Personne personne = personneRepository.findPersonneByMail(mail);
        if (personne == null) {
            throw new UsernameNotFoundException(mail);
        }
        return new User(personne.getMail(), personne.getMdph5(), emptyList());
    }

}