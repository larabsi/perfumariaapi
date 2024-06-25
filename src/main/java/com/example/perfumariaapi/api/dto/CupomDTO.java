package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Cupom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CupomDTO {
    private Long id;
    private String desconto;
    private String dataExpiracao;
    private String codigo;


    public static CupomDTO create(Cupom cupom) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(cupom, CupomDTO.class);
    }

}
