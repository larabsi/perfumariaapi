package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.ListaProdutosVenda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListaProdutosVendaDTO {
    private Long id;
    private Double quantidade;
    private Double valor;
    private Long idVendas;
    private Long idProduto;

    public static ListaProdutosVendaDTO create(ListaProdutosVenda listaProdutosVenda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(listaProdutosVenda, ListaProdutosVendaDTO.class);
    }

}
