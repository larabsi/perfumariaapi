package com.example.perfumariaapi.api.dto;

import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.model.entity.Classificacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoDTO {
    private Long id;
    private String cargo;


    public static CargoDTO create(Cargo cargo) {
        ModelMapper modelMapper = new ModelMapper();
        CargoDTO dto = modelMapper.map(cargo, CargoDTO.class);
        return dto;
    }
}
