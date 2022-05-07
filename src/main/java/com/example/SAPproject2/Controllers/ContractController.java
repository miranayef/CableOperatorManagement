package com.example.SAPproject2.Controllers;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Services.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(path = "contract")
public class ContractController {
    private final ContractService contractService;

    @Autowired
    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping
    public List<Contract> getAllContracts()
    {
        return contractService.getAllContracts();
    }

    @GetMapping(path = "{id}")
    public Contract getContract(@PathVariable("id") int id){
        return contractService.getContractById(id);
    }

    @DeleteMapping(path = "{id}")
    private void deleteContract(@PathVariable("id") int id)
    {
        contractService.delete(id);
    }

    @PostMapping
    private ResponseEntity<String> addNewContract(@RequestBody Contract contract)
    {
        contractService.saveOrUpdate(contract);
        return ResponseEntity.ok("Added contract successfully");
    }

    //assign packet to contract
    @PostMapping(path = "/{id}/packets/{packet_id}")
    private void AssignPacketToContract(
            @PathVariable int id,
            @PathVariable int packet_id
    ){
        contractService.AssignPacketToContract(id,packet_id);
    }

    //assign provider to contract
    @PostMapping(path = "/{id}/providers/{provider_id}")
    private void AssignProviderToContract(
            @PathVariable int id,
            @PathVariable int provider_id
    ){
        contractService.AssignProviderToContract(id,provider_id);
    }

    @PutMapping(path = "{id}")
    public void updateContract(@PathVariable("id") int id,
                               @RequestParam(required = false) Date endDate,
                               @RequestParam(required = false) Providers providers,
                               @RequestParam(required = false) List<Customers> customers,
                               @RequestParam(required = false)  Packets packets){
        contractService.updateContract(id, endDate, providers,customers,packets);
    }
}