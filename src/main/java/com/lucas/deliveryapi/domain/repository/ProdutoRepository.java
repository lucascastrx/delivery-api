package com.lucas.deliveryapi.domain.repository;

import com.lucas.deliveryapi.domain.model.Produto;
import com.lucas.deliveryapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("from Produto where restaurante.id = :restauranteId and id = :produtoId")
    Optional<Produto> findById(Long restauranteId, Long produtoId);

    List<Produto> findProdutosByRestaurante(Restaurante restaurante);
}
