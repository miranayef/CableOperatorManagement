package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.Customers;
import com.example.SAPproject2.Repositories.ReportRepository;
import com.example.SAPproject2.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customers> getAllCustomers()
    {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "{id}")
    public Customers getCustomers(@PathVariable("id") int id){
        return customerService.getCustomersById(id);
    }

    //creating delete mapping that deletes a specified book
    @DeleteMapping(path = "{id}")
    private void deleteCustomer(@PathVariable("id") int id)
    {
        customerService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewCustomer(@Valid @RequestBody Customers customers)
    {
        customerService.saveOrUpdate(customers);
        return ResponseEntity.ok("Added customer successfully");
    }

    //linking many to many rlt with contracts
    @PostMapping("/{id}/contracts/{contract_id}")
    private void assignContracts(
            @PathVariable int id,
            @PathVariable int contract_id
    ) {
        customerService.assignContracts(id, contract_id);
    }

    @PutMapping(path = "{id}")
    public void updateCustomer(@PathVariable("id") int id, @Valid @RequestBody Customers customer){
            customerService.updateCustomer(id, customer);
    }

//    @GetMapping(path = "/{firstName}/{lastName}")
//    public List<Customers> selectCustomerWithName(@PathVariable("firstName") String firstName,
//                                           @PathVariable("lastName") String lastName){
//       return customerService.selectCustomerWithName(firstName, lastName);
//    }
}