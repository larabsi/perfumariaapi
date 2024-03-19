package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.PerdaDTO;
import com.example.perfumariaapi.model.entity.Perda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerdaController {
    private Integer id;
    private String data;
    private Long idProduto;

    public Perda converter(PerdaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Perda perda = modelMapper.map(dto, Perda.class);

        return perda;
    }
}
