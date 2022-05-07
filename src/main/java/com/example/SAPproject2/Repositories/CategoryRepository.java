package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer>{
}
