package com.example.perfumariaapi.service;
import com.example.perfumariaapi.exception.RegraNegocioException;
import com.example.perfumariaapi.exception.SenhaInvalidaException;
import com.example.perfumariaapi.model.entity.Usuario;
import com.example.perfumariaapi.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private static UsuarioRepository repository;

    public UsuarioService(UsuarioRepository usuarioRepository){this.repository = usuarioRepository;}
    public List<Usuario> getUsuarios(){ return repository.findAll();}

    public Optional<Usuario> getUsuarioById(Long id){ return repository.findById(id); }



    @Transactional
    public Usuario salvar(Usuario usuario){
        validar(usuario);
        return repository.save(usuario);
    }

    @Transactional
    public void excluir(Usuario usuario) {
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }
    public void validar(Usuario usuario) {
        if (usuario.getCpf()== null|| usuario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("Cpf inválido");
        }
        if (usuario.getLogin()== null|| usuario.getLogin().trim().equals("")) {
            throw new RegraNegocioException("Login inválido");
        }
        if (usuario.getSenha()== null|| usuario.getSenha().trim().equals("")) {
            throw new RegraNegocioException("Senha inválida");
        }
    }


    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches(usuario.getSenha(), user.getPassword());

        if (senhasBatem){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = usuario.isAdmin()
                ? new String[]{"ADMIN", "USER"}
                : new String[]{"USER"};

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }

}