package com.example.SAPproject2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Contracts")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name="provider_id")
    private Providers providers;

    @ManyToOne
    @JoinColumn(name = "packet_id")
    private Packets packets;

    @JsonIgnore
    @ManyToMany(mappedBy = "contracts")
    private List<Customers> customers;

    public Contract(Date endDate, Providers providers, List<Customers> customers, Packets packets) {
        this.endDate = endDate;
        this.providers = providers;
        this.customers = customers;
        this.packets = packets;
    }

}

