package com.example.perfumariaapi.service;
import com.example.perfumariaapi.api.dto.ClassificacaoDTO;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.model.entity.Produto;
import com.example.perfumariaapi.model.repository.ClassificacaoRepository;
import com.example.perfumariaapi.model.entity.Classificacao;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClassificacaoService {
        private final ClassificacaoRepository repository;
        public ClassificacaoService(ClassificacaoRepository repository){this.repository = repository;}
        public List<Classificacao> getClassificacoes(){ return repository.findAll();}
        public Optional<Classificacao> getClassificacaoById(Long id){ return repository.findById(id); }
        public List<Classificacao> getClassificacoesByProduto(Optional<Produto> produto) {
                return repository.findByProdutos(produto);
        }
        @Transactional
        public Classificacao salvar(Classificacao classificacao){
                validar(classificacao);
                return repository.save(classificacao);
        }

        @Transactional
        public void excluir(Classificacao classificacao) {
                Objects.requireNonNull(classificacao.getId());
                repository.delete(classificacao);
        }

        public void validar(Classificacao classificacao) {
                if (classificacao.getDescricao() == null || classificacao.getDescricao().trim().equals("")) {
                        throw new RegraNegocioException("Descrição inválida");
                }
        }
}