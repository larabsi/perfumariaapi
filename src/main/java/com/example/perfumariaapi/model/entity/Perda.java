package com.example.perfumariaapi.model.entity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Perda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String data;
    private String codigoBarras;


    @ManyToOne
    private Produto produto;
    @ManyToOne
    private TipoPerda tipoPerda;

}