package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Entities.Enums.Gender;
import com.example.SAPproject2.Exceptions.BadRequestException;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.ContractRepository;
import com.example.SAPproject2.Repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ReportService reportService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customers> getAllCustomers()
    {
        return (List<Customers>) customerRepository.findAll();
    }

    //getting a specific record by using the method findById() of CrudRepository
    public Customers getCustomersById(int id)
    {
        if(!customerRepository.existsById(id))
        {
            throw new NotFoundException(
                    "Customer with id " + id + " does not exist");
        }
        return customerRepository.findById(id).get();
    }
    //saving a specific record by using the method save() of CrudRepository
    public ResponseEntity<?> saveOrUpdate(Customers customers)
    {
        Boolean existsEmail = customerRepository.selectExistsEmail(customers.getEmail());
        if (existsEmail) {
            throw new BadRequestException(
                    "Email " + customers.getEmail() + " taken");
        }
        return ResponseEntity.ok(customerRepository.save(customers));
    }

    //deleting a specific record by using the method deleteById() of CrudRepository
    public void delete(int id)
    {
        if(!customerRepository.existsById(id))
            throw new NotFoundException("Customer with id " + id + " does not exist");

        customerRepository.deleteById(id);
    }
    //updating a record
    @Transactional
    public void updateCustomer(int id, String firstName, String lastName, Gender gender, String email, String phone, String address, Reports reports, List<Contract> contracts) {
        Customers customers = customerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "customer with id " + id + " doesn't exist"));
        if (firstName != null && firstName.length() > 0 && !Objects.equals(customers.getFirstName(), firstName)) {
            customers.setFirstName(firstName);
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(customers.getLastName(), lastName)) {
            customers.setLastName(lastName);
        }
        if (gender != null && !Objects.equals(customers.getGender(), gender)) {
            customers.setGender(gender);
        }
        if (email != null && email.length() > 0 && !Objects.equals(customers.getEmail(), email)) {
            customers.setEmail(email);
        }
        if (phone != null && phone.length() > 0 && !Objects.equals(customers.getPhone(), phone)) {
            customers.setPhone(phone);
        }
        if (address != null && address.length() > 0 && !Objects.equals(customers.getAddress(), address)) {
            customers.setPhone(address);
        }
        if (reports != null && !Objects.equals(customers.getReports(), reports)) {
            customers.setReports(reports);
        }
        if (contracts != null && !Objects.equals(customers.getContracts(), contracts)) {
            customers.setContracts(contracts);
        }

    }

    public void assignContracts(int id, int contract_id) {
        Customers customers  = customerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "customer with id " + id + " doesn't exist"));
        Contract contracts = contractRepository.findById(contract_id).orElseThrow(() -> new NotFoundException(
                "contract with id " + contract_id + " doesn't exist"));

        //add or update report everytime a new contract is added to a customer
        Reports reports = new Reports(null, getCustomersById(id));
        reportService.saveOrUpdate(reports);
        reports.setMonthlyPayment(contractRepository.findById(contract_id).get().getPackets().getPacketPrice());
        customers.setReports(reports);

        customers.getContracts().add(contracts);
        customerRepository.save(customers);
    }
}