package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.TVchannels;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TVChannelRepository extends CrudRepository<TVchannels, Integer> {
}
