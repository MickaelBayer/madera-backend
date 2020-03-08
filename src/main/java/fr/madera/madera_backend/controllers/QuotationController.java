package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.ProjectModule;
import fr.madera.madera_backend.entities.Quotation;
import fr.madera.madera_backend.repositories.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotation")
public class QuotationController
{
    private QuotationRepository quotationRepository;

    @Autowired
    public QuotationController(QuotationRepository quotationRepository)
    {
        this.quotationRepository = quotationRepository;
    }

    @GetMapping
    public ResponseEntity<List<Quotation>> getQuotation() {
        return new ResponseEntity<List<Quotation>>(
                (List<Quotation>)this.quotationRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quotation> getQuotationById(@PathVariable Long id) {
        Quotation quotation = this.quotationRepository.findById(id).orElse(null);
        if (quotation != null) {
            return new ResponseEntity<Quotation>(quotation, HttpStatus.OK);
        } else {
            return new ResponseEntity<Quotation>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<List<Quotation>> getByProject(@PathVariable Long id) {
        return new ResponseEntity<List<Quotation>>(
                (List<Quotation>)this.quotationRepository.findByProject(id),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Quotation> createQuotation(@RequestBody Quotation quotation) {
        quotation = this.quotationRepository.save(quotation);
        System.out.println("Quotation created : " + quotation.getId());
        return new ResponseEntity<Quotation>(quotation, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Quotation> updateQuotation(@RequestBody Quotation quotation) {
        if ( this.quotationRepository.existsById(quotation.getId()) ) {
            this.quotationRepository.save(quotation);
            return new ResponseEntity<Quotation>(quotation, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuotation(@PathVariable Long id) {
        Quotation quotation = this.quotationRepository.findById(id).orElse(null);
        if (quotation != null) {
            this.quotationRepository.delete(quotation);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
