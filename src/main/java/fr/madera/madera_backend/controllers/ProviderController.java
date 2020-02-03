package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Provider;
import fr.madera.madera_backend.repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
public class ProviderController
{
    private ProviderRepository providerRepository;

    @Autowired
    public ProviderController(ProviderRepository providerRepository)
    {
        this.providerRepository = providerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Provider>> getProvider() {
        return new ResponseEntity<List<Provider>>(
                (List<Provider>)this.providerRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
        Provider provider = this.providerRepository.findById(id).orElse(null);
        if (provider != null) {
            return new ResponseEntity<Provider>(provider, HttpStatus.OK);
        } else {
            return new ResponseEntity<Provider>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Provider> createProvider(@RequestBody Provider provider) {
        provider = this.providerRepository.save(provider);
        System.out.println("Component Family created : " + provider.getId());
        return new ResponseEntity<Provider>(provider, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Provider> updateProvider(@RequestBody Provider provider) {
        if ( this.providerRepository.existsById(provider.getId()) ) {
            this.providerRepository.save(provider);
            return new ResponseEntity<Provider>(provider, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
        Provider provider = this.providerRepository.findById(id).orElse(null);
        if (provider != null) {
            this.providerRepository.delete(provider);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
