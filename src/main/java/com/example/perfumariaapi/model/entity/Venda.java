package com.example.perfumariaapi.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String data;
    private String valor_total;
    private String formaPagamento;

    @ManyToOne
    private Cupom cupom;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private ListaProdutosVenda listaProdutosVenda;
}
