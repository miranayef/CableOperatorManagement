package com.example.SAPproject2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Packets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Packets {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 255)
    private String name;
    private BigDecimal packetPrice;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private Category category;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "packets_channels",
            joinColumns = @JoinColumn(name = "packets_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"))
    public List<TVchannels> tVchannels;

    @JsonIgnore
    @OneToMany(mappedBy = "packets")
    private List<Contract> contracts;

    public Packets(String name, BigDecimal packetPrice, Category category, List<TVchannels> tVchannels, List<Contract> contracts) {
        this.name = name;
       this.packetPrice = packetPrice;
        this.category = category;
        this.tVchannels = tVchannels;
        this.contracts = contracts;
    }
}
