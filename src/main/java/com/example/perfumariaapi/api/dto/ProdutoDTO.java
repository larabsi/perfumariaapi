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
    private Integer id;
    private String nome;
    private String codigoBarras;


    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(produto, ProdutoDTO.class);
    }

}
