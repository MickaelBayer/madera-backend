package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Ranges;
import fr.madera.madera_backend.repositories.RangesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ranges")
public class RangesController
{
    private RangesRepository rangesRepository;

    @Autowired
    public RangesController(RangesRepository rangesRepository)
    {
        this.rangesRepository = rangesRepository;
    }

    @GetMapping
    public ResponseEntity<List<Ranges>> getRanges() {
        return new ResponseEntity<List<Ranges>>(
                (List<Ranges>)this.rangesRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranges> getRangesById(@PathVariable Long id) {
        Ranges ranges = this.rangesRepository.findById(id).orElse(null);
        if (ranges != null) {
            return new ResponseEntity<Ranges>(ranges, HttpStatus.OK);
        } else {
            return new ResponseEntity<Ranges>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Ranges> createRanges(@RequestBody Ranges ranges) {
        ranges = this.rangesRepository.save(ranges);
        System.out.println("Component Family created : " + ranges.getId());
        return new ResponseEntity<Ranges>(ranges, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Ranges> updateRanges(@RequestBody Ranges ranges) {
        if ( this.rangesRepository.existsById(ranges.getId()) ) {
            this.rangesRepository.save(ranges);
            return new ResponseEntity<Ranges>(ranges, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRanges(@PathVariable Long id) {
        Ranges ranges = this.rangesRepository.findById(id).orElse(null);
        if (ranges != null) {
            this.rangesRepository.delete(ranges);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
