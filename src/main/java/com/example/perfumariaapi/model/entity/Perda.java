package com.example.perfumariaapi.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Perda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // pega a descricao da perda e o cogigo de barras do produto
    private String data;

}