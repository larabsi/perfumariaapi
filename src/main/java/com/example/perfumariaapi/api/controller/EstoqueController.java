package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.EstoqueDTO;
import com.example.perfumariaapi.model.entity.Estoque;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueController {
    private Long id;
    private Integer quantidade;
    private Integer capacidadeMaxima;
    private Integer capacidadeMinima;
    private Integer pontoDeRessuprimento;
    private Long idProduto;  // Para identicar qual o produto do estoque

    public Estoque converter(EstoqueDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Estoque estoque = modelMapper.map(dto, Estoque.class);

        return estoque;
    }
}
