package com.example.perfumariaapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nome;
    private String codigoBarras;
    private String capacidadeMaxima;

    @ManyToOne
    private Estoque estoque;

    @ManyToOne
    private Tamanho tamanho;

    @ManyToOne
    private Classificacao classificacao;

    @ManyToOne
    private Fragrancia fragrancia;

    @ManyToOne
    private Fornecedor fornecedor;




}