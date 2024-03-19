package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.model.entity.Classificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoController {
    private Integer id;
    private String descricao;
    private Long idProduto;
    private String nome;

    public Classificacao converter(ClassificacaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Classificacao classificacao = modelMapper.map(dto, Classificacao.class);

        return classificacao;
    }
}
