package com.example.SAPproject2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "TVchannels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVchannels {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    public BigDecimal price;

    @ManyToOne
    @JoinColumn(name="category_id", referencedColumnName = "id")
    private Category category;

    @JsonIgnore
    @ManyToMany(mappedBy = "tVchannels")
    private List<Providers> providers;

    @JsonIgnore
    @ManyToMany(mappedBy = "tVchannels")
    private List<Packets> packets;

    public TVchannels(String name, BigDecimal price, Category category, List<Providers> providers, List<Packets> packets) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.providers = providers;
        this.packets = packets;
    }
}
