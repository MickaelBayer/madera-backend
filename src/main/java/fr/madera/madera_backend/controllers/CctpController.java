package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Cctp;
import fr.madera.madera_backend.entities.User;
import fr.madera.madera_backend.repositories.CctpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cctp")
public class CctpController
{
    private CctpRepository cctpRepository;

    @Autowired
    public CctpController(CctpRepository cctpRepository)
    {
        this.cctpRepository = cctpRepository;
    }

    @GetMapping
    public ResponseEntity<List<Cctp>> getCctp() {
        return new ResponseEntity<List<Cctp>>(
                (List<Cctp>)this.cctpRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/find")
    public Iterable<Cctp> findall(){
        return this.cctpRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cctp> getCctpById(@PathVariable Long id) {
        Cctp cctp = this.cctpRepository.findById(id).orElse(null);
        if (cctp != null) {
            return new ResponseEntity<Cctp>(cctp, HttpStatus.OK);
        } else {
            return new ResponseEntity<Cctp>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Cctp> createCctp(@RequestBody Cctp cctp) {
        cctp = this.cctpRepository.save(cctp);
        System.out.println("Component Family created : " + cctp.getId());
        return new ResponseEntity<Cctp>(cctp, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cctp> updateCctp(@RequestBody Cctp cctp) {
        if ( this.cctpRepository.existsById(cctp.getId()) ) {
            this.cctpRepository.save(cctp);
            return new ResponseEntity<Cctp>(cctp, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCctp(@PathVariable Long id) {
        Cctp cctp = this.cctpRepository.findById(id).orElse(null);
        if (cctp != null) {
            this.cctpRepository.delete(cctp);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
