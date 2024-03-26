package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.CupomDTO;
import com.example.perfumariaapi.model.entity.Cupom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomController {


    public Cupom converter(CupomDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Cupom cupom = modelMapper.map(dto, Cupom.class);

        return cupom;
    }
}
