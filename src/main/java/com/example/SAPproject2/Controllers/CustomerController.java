package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.Contract;
import com.example.SAPproject2.Entities.Customers;
import com.example.SAPproject2.Entities.Enums.Gender;
import com.example.SAPproject2.Entities.Reports;
import com.example.SAPproject2.Repositories.ReportRepository;
import com.example.SAPproject2.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
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
    private void deleteBook(@PathVariable("id") int id)
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
    public void updateCustomer(@PathVariable("id") int id,
                               @RequestParam(required = false) String firstName,
                               @RequestParam(required = false) String lastName,
                               @RequestParam(required = false) Gender gender,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String phone,
                               @RequestParam(required = false) String address,
                               @RequestParam(required = false) Reports reports,
                               @RequestParam(required = false) List<Contract> contracts){
            customerService.updateCustomer(id, firstName, lastName,gender,email,phone,address,reports,contracts);
    }
}
