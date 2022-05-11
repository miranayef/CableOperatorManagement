package com.example.SAPproject2.Entities;

import com.example.SAPproject2.Entities.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customers {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private int id;
        @Column(nullable = false, length = 255)
        @Size(min = 2, message = "Name should be at least 2 characters long")
        @NotBlank(message = "first name can't be blank")
        private String firstName;
        @Column(nullable = false)
        @Size(min = 2, message = "Name should be at least 2 characters long")
        @NotBlank(message = "last name can't be blank")
        private String lastName;
        @Enumerated(EnumType.STRING)
        private Gender gender;
        @Email
        @NotBlank(message = "Email can't be blank")
        private String email;
        @Column(length = 20)
        @Size(min = 8, message = "Please enter a valid phone number")
        private String phone;
        private String address;

        @JsonIgnore
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "report_id", referencedColumnName = "id")
        private Reports reports;

        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "customers_contracts",
                joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "contracts_id", referencedColumnName = "id"))
        private List<Contract> contracts;


        public Customers(String firstName, String lastName, Gender gender, String email, String phone, String address, Reports reports, List<Contract> contracts) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gender = gender;
            this.email = email;
            this.phone = phone;
            this.address = address;
            this.reports = reports;
            this.contracts = contracts;
        }

}
