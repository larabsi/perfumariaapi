package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Estoque;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueDTO {
    private Long id;
    private Integer quantidade;
    private Integer capacidadeMaxima;
    private Integer capacidadeMinima;
    private Integer pontoDeRessuprimento;
    private Long idProduto;  // Para identicar qual o produto do estoque

    public static EstoqueDTO create(Estoque estoque) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(estoque, EstoqueDTO.class);
    }

}