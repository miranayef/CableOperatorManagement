package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Exceptions.BadRequestException;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.ContractRepository;
import com.example.SAPproject2.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

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

    public void updateCustomer(int id, Customers newCustomer) {
        Customers customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "customer with id " + id + " doesn't exist"));

        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setGender(newCustomer.getGender());
        customer.setEmail(newCustomer.getEmail());
        customer.setPhone(newCustomer.getPhone());
        customer.setAddress(newCustomer.getAddress());
        customer.setContracts(newCustomer.getContracts());
        customer.setReports(newCustomer.getReports());

        customerRepository.save(customer);
    }

    @Transactional
    public void assignContracts(int id, int contract_id) {
        Customers customer  = customerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "customer with id " + id + " doesn't exist"));
        Contract contract = contractRepository.findById(contract_id).orElseThrow(() -> new NotFoundException(
                "contract with id " + contract_id + " doesn't exist"));
        if(contractRepository.findById(contract_id).get().getPackets().equals(null))
            throw new BadRequestException("Contract with id"+ contract_id + " can't be added");

        //add or update report everytime a new contract is added to a customer
        if(customer.getReports()!=null && !"".equals(customer.getReports())){
            BigDecimal sum = customerRepository.findById(id).get().getReports().getMonthlyPayment().add(contractRepository.findById(contract_id).get().getPackets().getPacketPrice());
            customerRepository.findById(id).get().getReports().setMonthlyPayment(sum);
        }else{
        Reports reports = new Reports(null, getCustomersById(id));
        reportService.saveOrUpdate(reports);
        reports.setMonthlyPayment(contractRepository.findById(contract_id).get().getPackets().getPacketPrice());
        customer.setReports(reports);}

        customer.getContracts().add(contract);
        customerRepository.save(customer);
    }

}