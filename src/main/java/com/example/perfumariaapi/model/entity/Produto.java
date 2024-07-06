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

    @ManyToOne
    private Estoque estoque;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Classificacao> classificacao;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Fornecedor> fornecedor;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Estoque> estoques;

    @JsonIgnore
    @OneToMany(mappedBy = "produto")
    private List<Fragrancia> fragrancia;

}