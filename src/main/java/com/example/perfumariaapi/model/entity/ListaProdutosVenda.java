package com.example.perfumariaapi.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProdutosVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double quantidade;
    private double valor;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    @JoinColumn (name="venda_id")
    private Venda venda;

}
