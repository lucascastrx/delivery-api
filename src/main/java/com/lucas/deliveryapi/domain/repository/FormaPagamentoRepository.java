package com.lucas.deliveryapi.domain.repository;

import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {


}
