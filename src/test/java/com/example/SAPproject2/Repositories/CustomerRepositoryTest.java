package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Customers;
import com.example.SAPproject2.Entities.Enums.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @AfterEach
    void teardown(){
        underTest.deleteAll();
    }

    @Test
    void CheckIfEmailExists() {
        String email = "i_ivanov@gmail.com";
        Customers customers = new Customers(
                "Iliyan",
                "Ivanov",
                Gender.Male,
                email,
                "0898475497",
                "Sofia, Bulgaria",
                null,
                null
        );
        underTest.save(customers);
        boolean expected =  underTest.selectExistsEmail(email);
        assertThat(expected).isTrue();
    }

    @Test
    void CheckIfEmailNotExists() {
        String email = "i_i@gmail.com";
        boolean expected =  underTest.selectExistsEmail(email);
        assertThat(expected).isFalse();
    }
}