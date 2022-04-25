package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.produto.input.ProdutoInputDTO;
import com.lucas.deliveryapi.api.model.produto.output.ProdutoDTO;
import com.lucas.deliveryapi.domain.model.Produto;
import com.lucas.deliveryapi.domain.repository.ProdutoRepository;
import com.lucas.deliveryapi.domain.service.ProdutoService;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private ProdutoRepository produtoRepository;

    private ProdutoService produtoService;

    private RestauranteService restauranteService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<ProdutoDTO> list(@PathVariable Long restauranteId){
        var restaurante = restauranteService.findById(restauranteId);
        return mapperDTO.toCollection(produtoRepository.findProdutosByRestaurante(restaurante),
                ProdutoDTO.class);

    }

    @GetMapping("/{produtoId}")
    public ProdutoDTO findById(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        var restaurante = restauranteService.findById(restauranteId);
        var produto = produtoService.findById(restaurante.getId(), produtoId);
        return mapperDTO.transform(produto, ProdutoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDTO addProduto(@PathVariable Long restauranteId,
                                 @RequestBody @Valid ProdutoInputDTO produtoInputDTO){
        var restaurante = restauranteService.findById(restauranteId);
        var produto = mapperDTO.transform(produtoInputDTO, Produto.class);
        produto.setRestaurante(restaurante);
        produto = produtoService.addProduto(produto);
        return mapperDTO.transform(produto, ProdutoDTO.class);
    }

    @PutMapping("/{produtoId}")
    public ProdutoDTO update(@PathVariable Long restauranteId, @PathVariable Long produtoId,
                             @RequestBody @Valid ProdutoInputDTO produtoInputDTO){
        var produto = produtoService.findById(restauranteId, produtoId);
        mapperDTO.copyToDomain(produtoInputDTO, produto);
        produto = produtoService.addProduto(produto);
        return mapperDTO.transform(produto, ProdutoDTO.class);
    }


}
