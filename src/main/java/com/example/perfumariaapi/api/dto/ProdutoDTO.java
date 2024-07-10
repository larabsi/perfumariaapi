package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String nome;
    private String codigoBarras;
    private Long idEstoque;
    private Long idTamanho;
    private Long idClassificacao;
    private Long idFragrancia;
    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }
}