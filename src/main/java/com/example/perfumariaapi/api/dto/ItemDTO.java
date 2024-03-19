package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private Long id;

    private double quantidade;
    private double valor;
    private Long idVendas;
    private Long idProduto;

    public static ItemDTO create(Item item) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(item, ItemDTO.class);
    }

}
