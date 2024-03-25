package com.example.perfumariaapi.service;

import com.example.perfumariaapi.model.entity.Item;
import com.example.perfumariaapi.model.repository.FragranciaRepository;
import com.example.perfumariaapi.model.repository.ItemRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService{
    private ItemRepository repository;

    public ItemService(ItemRepository itemRepository) {
        this.repository = repository;
    }

    public List<Item> getItem() {
        return repository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return repository.findById(id);
    }


}
