package com.example.vehiclerestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="market_price")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleMarketPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int modelYear;
    private String brandName;
    private String modelName;
    private String trimType;
    private double price;

}
