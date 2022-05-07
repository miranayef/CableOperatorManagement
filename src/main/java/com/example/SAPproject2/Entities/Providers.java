package com.example.SAPproject2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Providers {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotBlank
    @Size(min = 2, message = "name mut be at least 2 characters long")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "providers")
    private List<Contract> contracts;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "providers_channels",
            joinColumns = @JoinColumn(name = "provider_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "channels_id", referencedColumnName = "id"))
    public List<TVchannels> tVchannels;

    public Providers(String name, List<Contract> contracts, List<TVchannels> tVchannels) {
        this.name = name;
        this.contracts = contracts;
        this.tVchannels = tVchannels;
    }
}

