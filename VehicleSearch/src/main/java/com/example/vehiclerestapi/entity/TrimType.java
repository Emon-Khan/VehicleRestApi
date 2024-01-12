package com.example.vehiclerestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trim_types")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrimType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "trim_type")
    private String trimType;
    /*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "model_trim", joinColumns = @JoinColumn(name = "trim_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "model_id", referencedColumnName = "id"))
    private List<Model> modelList;*/
}
