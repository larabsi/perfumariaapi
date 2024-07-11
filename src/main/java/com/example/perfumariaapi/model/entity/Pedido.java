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
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String valor;
    private String dataPedido;
    private String dataEntrega;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Fornecedor fornecedor;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido")
    private List<Fornecedor> fornecedores;

    @JsonIgnore
    @OneToMany(mappedBy = "pedido")
    private List<Fornecedor> produtos;

}
