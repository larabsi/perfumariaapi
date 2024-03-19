package com.example.perfumariaapi.api.dto;
import com.example.perfumariaapi.model.entity.Classificacao;
import com.example.perfumariaapi.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String numeroTelefone;
    private String dataNascimento;
    private Long idVendas;
    private Date data;
    private Double valor;



    public static ClienteDTO create(Cliente cliente) {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);
        dto.data = cliente.getVendas().getData();
        dto.valor = cliente.getVendas().getValorTotal();
        return dto;
    }

}
