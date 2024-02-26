package com.example.perfumariaapi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
       // lista de produtos que vende
    private String nome;
    private Integer cnpj;
    private String email;
    private String numeroTelefone;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
}