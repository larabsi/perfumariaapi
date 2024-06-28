package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaDTO {
    private Long id;
    private String data;
    private String valorTotal;
    private String formaPagamento;
    private Long idCupom;
    private Long idFuncionario;
    private Long idCliente;
    private Long idProduto;

    public static VendaDTO create(Venda venda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(venda, VendaDTO.class);
    }
}
