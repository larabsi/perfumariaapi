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
public class Tamanho {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String volume;


    @JsonIgnore
    @OneToMany(mappedBy = "tamanho")
    private List<Produto> produtos;
}
