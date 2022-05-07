package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Customers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customers, Integer> {
    //check if email already exists
    @Query("" +
            "SELECT CASE WHEN COUNT(c) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM Customers c " +
            "WHERE c.email = ?1"
    )
    Boolean selectExistsEmail(String email);
}
