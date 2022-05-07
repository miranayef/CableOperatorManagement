package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Contract;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ContractRepository extends CrudRepository<Contract, Integer> {
}
