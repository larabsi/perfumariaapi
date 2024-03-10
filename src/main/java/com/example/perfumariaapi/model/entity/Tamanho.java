package com.example.perfumariaapi.model.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Tamanho {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String volume;
    @ManyToOne
    private Produto produto;
}
