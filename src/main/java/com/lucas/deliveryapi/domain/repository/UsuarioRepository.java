package com.lucas.deliveryapi.domain.repository;

import com.lucas.deliveryapi.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
