package com.example.perfumariaapi.model.entity;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Classificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "classificacao")
    private List<Produto> produtos;
}