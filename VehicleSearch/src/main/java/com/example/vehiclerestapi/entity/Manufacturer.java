package com.example.vehiclerestapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "manufacturer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name field can't be blank")
    @Column(name="manufacturer_name")
    private String manufacturerName;
    @NotBlank(message = "CountryOfOrigin field can't be blank")
    @Column(name="country_of_origin")
    private String countryOfOrigin;
}
