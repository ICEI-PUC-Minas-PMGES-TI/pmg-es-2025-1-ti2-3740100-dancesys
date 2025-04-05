package com.dancesys.dancesys.repository;

import com.dancesys.dancesys.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
    Usuario findByEmailAndSenha(String email, String senha);
}
