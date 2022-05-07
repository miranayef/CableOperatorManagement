package com.example.SAPproject2.Services;

import com.example.SAPproject2.Entities.*;
import com.example.SAPproject2.Exceptions.NotFoundException;
import com.example.SAPproject2.Repositories.ContractRepository;
import com.example.SAPproject2.Repositories.PacketRepository;
import com.example.SAPproject2.Repositories.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ContractService {
    private final ContractRepository contractRepository;

    @Autowired
    PacketRepository packetRepository;
    @Autowired
    ProviderRepository providerRepository;

    @Autowired
    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public List<Contract> getAllContracts()
    {
        return (List<Contract>) contractRepository.findAll();
    }


    public Contract getContractById(int id) {
        boolean exists = contractRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("contract with id "+ id + " doesn't exist");
        }
        return contractRepository.findById(id).get();
    }

    public void delete(int id) {
        boolean exists = contractRepository.existsById(id);
        if(!exists){
            throw new NotFoundException("customer with id "+ id + " doesn't exist");

        }
        contractRepository.deleteById(id);
    }

    public void saveOrUpdate(Contract contract) {
        contractRepository.save(contract);
    }

    @Transactional
    public void updateContract(int id, Date endDate, Providers providers, List<Customers> customers, Packets packets){
        Contract contract  = contractRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "contract with id " + id + " doesn't exist"));
        if (endDate != null && !Objects.equals(contract.getEndDate(), endDate)) {
            contract.setEndDate(endDate);
        }
        if (providers != null &&  !Objects.equals(contract.getProviders(), providers)) {
            contract.setProviders(providers);
        }
        if (customers != null && !Objects.equals(contract.getCustomers(), customers)) {
            contract.setCustomers(customers);
        }
        if (packets != null && !Objects.equals(contract.getPackets(), packets)) {
            contract.setPackets(packets);
        }

    }

    public void AssignPacketToContract(int id, int packet_id) {
        Contract contract = contractRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "contract with id " + id + " doesn't exist"));
        Packets packets = packetRepository.findById(packet_id).orElseThrow(() -> new NotFoundException(
                "packet with id " + packet_id + " doesn't exist"));
        contract.setPackets(packets);
        contractRepository.save(contract);
    }

    public void AssignProviderToContract(int id, int provider_id) {
        Contract contract = contractRepository.findById(id).orElseThrow(() -> new NotFoundException(
                "contract with id " + id + " doesn't exist"));
        Providers providers = providerRepository.findById(provider_id).orElseThrow(() -> new NotFoundException(
                "provider with id " + provider_id + " doesn't exist"));
        contract.setProviders(providers);
        contractRepository.save(contract);
    }
}
