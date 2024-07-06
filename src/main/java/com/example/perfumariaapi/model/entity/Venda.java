package com.example.perfumariaapi.model.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;
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
    private Double valor_total;
    private String formaPagamento;

    @ManyToOne
    private Cupom cupom;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Funcionario funcionario;
    @ManyToOne
    private Cliente cliente;

    @JsonIgnore
    @OneToMany(mappedBy = "venda")
    private List<Cliente> clientes;

    @JsonIgnore
    @OneToMany(mappedBy = "venda")
    private List<Funcionario> funcionarios;

    @JsonIgnore
    @OneToMany(mappedBy = "venda")
    private List<Item> item;
}
