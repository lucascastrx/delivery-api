package com.lucas.deliveryapi.domain.repository;

import com.lucas.deliveryapi.domain.model.Pedido;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{

    @Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
    List<Pedido> findAll();


//    @Query("from Pedido where codigo = :codigo")
    Optional<Pedido> findByCodigo(String codigo);
}