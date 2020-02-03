package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Equipment;
import fr.madera.madera_backend.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
public class EquipmentController
{
    private EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentController(EquipmentRepository equipmentRepository)
    {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Equipment>> getEquipment() {
        return new ResponseEntity<List<Equipment>>(
                (List<Equipment>)this.equipmentRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipment> getEquipmentById(@PathVariable Long id) {
        Equipment equipment = this.equipmentRepository.findById(id).orElse(null);
        if (equipment != null) {
            return new ResponseEntity<Equipment>(equipment, HttpStatus.OK);
        } else {
            return new ResponseEntity<Equipment>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Equipment> createEquipment(@RequestBody Equipment equipment) {
        equipment = this.equipmentRepository.save(equipment);
        System.out.println("Component Family created : " + equipment.getId());
        return new ResponseEntity<Equipment>(equipment, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Equipment> updateEquipment(@RequestBody Equipment equipment) {
        if ( this.equipmentRepository.existsById(equipment.getId()) ) {
            this.equipmentRepository.save(equipment);
            return new ResponseEntity<Equipment>(equipment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable Long id) {
        Equipment equipment = this.equipmentRepository.findById(id).orElse(null);
        if (equipment != null) {
            this.equipmentRepository.delete(equipment);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}