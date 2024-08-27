package com.example.perfumariaapi.model.repository;
import com.example.perfumariaapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String login);

}
