package com.example.perfumariaapi.model.entity;
import com.example.perfumariaapi.service.FuncionarioService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date data;
    private Double valorTotal;
    private String formaPagamento;

    @ManyToOne
    private Cupom cupom;

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private Funcionario funcionario;

    @ManyToOne
    private Cliente cliente;


}
