package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.ItemDTO;
import com.example.perfumariaapi.model.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemController {
    private Long id;
    private Double quantidade;
    private Double valor;
    private Long idVendas;
    private Long idProduto;

    public Item converter(ItemDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Item item = modelMapper.map(dto, Item.class);

        return item;
    }
}
