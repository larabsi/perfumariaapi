package com.example.perfumariaapi.model.entity;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

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
    private Long idProduto;

    @ManyToOne
    private Produto produto;


}
