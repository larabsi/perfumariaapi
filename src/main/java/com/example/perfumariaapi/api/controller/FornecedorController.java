package com.example.perfumariaapi.api.controller;
import com.example.perfumariaapi.api.dto.FornecedorDTO;
import com.example.perfumariaapi.model.entity.Fornecedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FornecedorController {
    private Long id;
    private String nome;
    private Integer cnpj;
    private String email;
    private String numeroTelefone;
    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private Long idProduto;

    public Fornecedor converter(FornecedorDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Fornecedor fornecedor = modelMapper.map(dto, Fornecedor.class);

        return fornecedor;
    }
}
