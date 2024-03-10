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
public class Perda {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String data;
    @ManyToOne
    private Produto produto;

}