package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cargo;
import com.example.perfumariaapi.model.repository.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CargoService {
    private CargoRepository repository;

    public CargoService(CargoRepository repository)
    {this.repository = repository;
    }
    public List<Cargo> getCargos(){
        return repository.findAll();
    }

    public Optional<Cargo> getCargoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Cargo salvar(Cargo cargo){
        validar(cargo);
        return repository.save(cargo);

    }

    @Transactional
    public void excluir(Cargo cargo) {
        Objects.requireNonNull(cargo.getId());
        repository.delete(cargo);
    }
    public void validar(Cargo cargo) {
        if (cargo.getCargo() == null || cargo.getCargo().trim().equals("")) {
            throw new RegraNegocioException("cargo inválido");
        }


    }


}
