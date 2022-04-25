package com.lucas.deliveryapi.domain.repository;

import com.lucas.deliveryapi.domain.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
}
