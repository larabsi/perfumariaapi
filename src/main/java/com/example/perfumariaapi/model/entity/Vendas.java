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
public class Vendas {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // id nomefuncionario e id nomecliente????
    //lista de produtos
    //pega o cupom de desconto

    private String data;
    private String valorTotal;
    private String formaPagamento;


}
