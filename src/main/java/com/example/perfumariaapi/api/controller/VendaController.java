package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.VendaDTO;
import com.example.perfumariaapi.model.entity.Venda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaController {
    private Integer id;
    private String data;
    private String valorTotal;
    private String formaPagamento;
    private Long idCupom;
    private Long idFuncionario;
    private Long idCliente;
    private Long idProduto;

    public Venda converter(VendaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Venda venda = modelMapper.map(dto, Venda.class);

        return venda;
    }
}
