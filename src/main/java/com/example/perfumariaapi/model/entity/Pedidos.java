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
public class Pedidos {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

     // qual o forncedor
    //lista dos produtos do pedido,contento lote,quantidade,valorU..
    private String valor;
    private String dataPedido;
    private String dataEntrega;

}
