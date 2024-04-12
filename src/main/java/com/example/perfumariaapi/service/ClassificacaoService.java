package com.example.perfumariaapi.service;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.repository.ClassificacaoRepository;
import com.example.perfumariaapi.model.entity.Classificacao;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClassificacaoService {

        private ClassificacaoRepository repository;

        public ClassificacaoService(ClassificacaoRepository classificacaoRepository){this.repository = classificacaoRepository;}
        public List<Classificacao> getClassificacao(){ return repository.findAll();}

        public Optional<Classificacao> getClassificacaoById(Long id){ return repository.findById(id); }

        @Transactional
        public Classificacao salvar(Classificacao classificacao){
                validar(classificacao);
                return repository.save(classificacao);



        }

        public void validar(Classificacao classificacao) {
                if (classificacao.getDescricao() == null || classificacao.getDescricao().trim().equals("")) {
                        throw new RegraNegocioException("Descrição inválida");
                }


}}
