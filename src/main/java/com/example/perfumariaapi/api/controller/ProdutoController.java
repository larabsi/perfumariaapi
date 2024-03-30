package com.example.perfumariaapi.api.controller;;
import com.example.perfumariaapi.api.dto.ProdutoDTO;
import com.example.perfumariaapi.model.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoController {
    public Produto converter(ProdutoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Produto produto = modelMapper.map(dto, Produto.class);

        return produto;
    }
}
