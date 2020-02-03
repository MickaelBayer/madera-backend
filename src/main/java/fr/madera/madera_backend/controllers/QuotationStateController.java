package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.QuotationState;
import fr.madera.madera_backend.repositories.QuotationStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quotationState")
public class QuotationStateController
{
    private QuotationStateRepository quotationStateRepository;

    @Autowired
    public QuotationStateController(QuotationStateRepository quotationStateRepository)
    {
        this.quotationStateRepository = quotationStateRepository;
    }

    @GetMapping
    public ResponseEntity<List<QuotationState>> getQuotationState() {
        return new ResponseEntity<List<QuotationState>>(
                (List<QuotationState>)this.quotationStateRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuotationState> getQuotationStateById(@PathVariable Long id) {
        QuotationState quotationState = this.quotationStateRepository.findById(id).orElse(null);
        if (quotationState != null) {
            return new ResponseEntity<QuotationState>(quotationState, HttpStatus.OK);
        } else {
            return new ResponseEntity<QuotationState>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<QuotationState> createQuotationState(@RequestBody QuotationState quotationState) {
        quotationState = this.quotationStateRepository.save(quotationState);
        System.out.println("Component Family created : " + quotationState.getId());
        return new ResponseEntity<QuotationState>(quotationState, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<QuotationState> updateQuotationState(@RequestBody QuotationState quotationState) {
        if ( this.quotationStateRepository.existsById(quotationState.getId()) ) {
            this.quotationStateRepository.save(quotationState);
            return new ResponseEntity<QuotationState>(quotationState, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuotationState(@PathVariable Long id) {
        QuotationState quotationState = this.quotationStateRepository.findById(id).orElse(null);
        if (quotationState != null) {
            this.quotationStateRepository.delete(quotationState);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
