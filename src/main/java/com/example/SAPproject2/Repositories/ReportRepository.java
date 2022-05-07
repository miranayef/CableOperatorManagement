package com.example.SAPproject2.Repositories;

import com.example.SAPproject2.Entities.Reports;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ReportRepository extends CrudRepository<Reports, Integer> {
    @Transactional
    @Modifying
    @Query(value = " UPDATE Reports SET monthlyPayment = :percentage * monthlyPayment")
    void discount(@Param("percentage") BigDecimal percentage);

    @Transactional
    @Modifying
    @Query(value = " UPDATE Reports SET monthlyPayment = :percentage * monthlyPayment  + monthlyPayment")
    void raisePrices(@Param("percentage") BigDecimal percentage);
}
