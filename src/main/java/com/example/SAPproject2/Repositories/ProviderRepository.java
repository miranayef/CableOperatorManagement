package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Providers;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProviderRepository  extends CrudRepository<Providers, Integer> {
}