package com.example.perfumariaapi.service;

import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Cliente;
import com.example.perfumariaapi.model.entity.Item;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import com.example.perfumariaapi.model.repository.ItemRepository;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService{
    private ItemRepository repository;

    public ItemService(ItemRepository itemRepository) {
        this.repository = itemRepository;
    }

    public List<Item> getItem() {
        return repository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Item salvar(Item item){
        validar(item);
        return repository.save(item);

    }

    @Transactional
    public void excluir(Item item) {
        Objects.requireNonNull(item.getId());
        repository.delete(item);
    }
    public void validar(Item item) {
        if (item.getProduto() == null || item.getProduto().getNome().trim().equals("")) {
            throw new RegraNegocioException("Item inválido - Produto Null");
        }
    }

}
