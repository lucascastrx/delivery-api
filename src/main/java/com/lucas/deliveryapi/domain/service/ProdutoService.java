package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.ProductNotFoundException;
import com.lucas.deliveryapi.domain.model.Produto;
import com.lucas.deliveryapi.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto addProduto(Produto produto){
        return produtoRepository.save(produto);
    }

    public Produto findById(Long restauranteId, Long produtoId){
        return produtoRepository.findById(restauranteId, produtoId).orElseThrow(()->
                new ProductNotFoundException(restauranteId,produtoId));
    }
}
