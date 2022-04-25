package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.PaymentMethodNotFoundException;
import com.lucas.deliveryapi.domain.model.FormaPagamento;
import com.lucas.deliveryapi.domain.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormaPagamentoService {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Transactional
    public FormaPagamento addFormaPagamento(FormaPagamento formaPagamento){
        return formaPagamentoRepository.save(formaPagamento);
    }

    public FormaPagamento findById(Long id){
        return formaPagamentoRepository.findById(id).orElseThrow(() ->
                new PaymentMethodNotFoundException(id));
    }

    @Transactional
    public void delete(Long id){
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityUnviableException("Entidade está em uso, id: " + id);
        } catch (EmptyResultDataAccessException e){
            throw new PaymentMethodNotFoundException(id);
        }
    }


}
