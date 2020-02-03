package fr.madera.madera_backend.controllers;

import fr.madera.madera_backend.entities.Customer;
import fr.madera.madera_backend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController
{
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getCustomer() {
        return new ResponseEntity<List<Customer>>(
                (List<Customer>)this.customerRepository.findAll(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer != null) {
            return new ResponseEntity<Customer>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<Customer>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customer = this.customerRepository.save(customer);
        System.out.println("Customer created : " + customer.getId());
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
        if ( this.customerRepository.existsById(customer.getId()) ) {
            this.customerRepository.save(customer);
            return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        if (customer != null) {
            this.customerRepository.delete(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}