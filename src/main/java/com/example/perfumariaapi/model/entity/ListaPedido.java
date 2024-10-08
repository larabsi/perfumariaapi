package com.example.perfumariaapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double quantidade;
    private double valor;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    @JoinColumn (name="pedido_id")
    private Pedido pedido;


}
