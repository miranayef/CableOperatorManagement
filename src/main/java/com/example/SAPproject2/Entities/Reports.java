package com.example.SAPproject2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reports {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private BigDecimal monthlyPayment;

    @JsonIgnore
    @OneToOne(mappedBy = "reports")
    private Customers customers;

    public Reports(BigDecimal monthlyPayment,  Customers customers) {
        this.monthlyPayment = monthlyPayment;
        this.customers = customers;
    }
}