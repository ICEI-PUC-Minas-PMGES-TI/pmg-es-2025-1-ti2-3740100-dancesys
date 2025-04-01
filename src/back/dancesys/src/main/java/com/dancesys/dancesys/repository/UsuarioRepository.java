package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.dto.UsuarioDto;
import com.dancesys.dancesys.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmailAndSenha(String email, String senha);

    Optional<Usuario> findById(Long id);
}
