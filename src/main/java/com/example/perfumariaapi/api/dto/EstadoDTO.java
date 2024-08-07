package com.example.perfumariaapi.api.dto;


import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.model.entity.Estado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstadoDTO {
        private Long id;
        private String nome;


    public static EstadoDTO create(Estado estado) {
        ModelMapper modelMapper = new ModelMapper();
        EstadoDTO dto = modelMapper.map(estado, EstadoDTO.class);
        return dto;
    }
}
