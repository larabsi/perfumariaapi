package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.entity.TipoPerda;
import com.example.perfumariaapi.model.repository.TipoPerdaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TipoPerdaService {
    private TipoPerdaRepository repository;

    public TipoPerdaService(TipoPerdaRepository repository) {
        this.repository = repository;
    }

    public List<TipoPerda> getTipoPerdas() {
        return repository.findAll();
    }

    public Optional<TipoPerda> getTipoPerdaById(Long id) { return repository.findById(id); }


    @Transactional
    public TipoPerda salvar(TipoPerda tipoPerda){
        validar(tipoPerda);
        return repository.save(tipoPerda);
    }

    @Transactional
    public void excluir(TipoPerda tipoPerda) {
        Objects.requireNonNull(tipoPerda.getId());
        repository.delete(tipoPerda);
    }

    public void validar(TipoPerda tipoPerda) {
        if (tipoPerda.getDescricao()==null|| tipoPerda.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválida");
        }
    }

}
