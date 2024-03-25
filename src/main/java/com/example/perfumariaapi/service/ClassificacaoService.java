package com.example.perfumariaapi.service;
import com.example.perfumariaapi.model.repository.ClassificacaoRepository;
import com.example.perfumariaapi.model.entity.Classificacao;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClassificacaoService {

        private ClassificacaoRepository repository;

        public ClassificacaoService(ClassificacaoRepository classificacaoRepository){this.repository = repository;}
        public List<Classificacao> getClassificacao(){ return repository.findAll();}

        public Optional<Classificacao> getClassificacaoById(Long id){ return repository.findById(id); }



}
