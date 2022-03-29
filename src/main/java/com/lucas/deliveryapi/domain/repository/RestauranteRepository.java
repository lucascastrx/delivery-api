package com.lucas.deliveryapi.domain.repository;

import com.lucas.deliveryapi.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
        RestauranteRepositoryCustomized,
        JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinhaId);

   // @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
    List<Restaurante> consultarPorNomeECozinhaId(String nome, @Param("id") Long cozinha);

    /**
     * @param nome
     * @return returns the first entity with the given @param
     */
    Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

    /**
     * @param nome
     * @return returns the first two occurrence with the given @param
     */
    List<Restaurante> findTop2ByNomeContaining(String nome);

//    @Query("from Restaurante r join fetch r.cozinha left join fetch r.formasPagamento")
//    List<Restaurante> findAll();
}
