package com.example.SAPproject2.Entities;

import com.example.SAPproject2.Entities.Enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Type type ;
    @Column
    public BigDecimal price;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<TVchannels> tVchannels;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Packets> packets;

    public Category(Type type, BigDecimal price, List<TVchannels> tVchannels, List<Packets> packets) {
        this.type = type;
        this.price = price;
        this.tVchannels = tVchannels;
        this.packets = packets;
    }
}

