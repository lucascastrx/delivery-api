package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.OrderNotFoundException;
import com.lucas.deliveryapi.domain.model.Pedido;
import com.lucas.deliveryapi.domain.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class EmissaoPedidoService {

    private PedidoRepository pedidoRepository;

    private RestauranteService restauranteService;

    private CidadeService cidadeService;

    private UsuarioService usuarioService;

    private ProdutoService produtoService;

    private FormaPagamentoService formaPagamentoService;


    public Pedido findByCodigo(String codigo){
        return pedidoRepository.findByCodigo(codigo).orElseThrow(()->
                new OrderNotFoundException(" "+ codigo)
        );
    }

    /**
     * Below we got some methods to validate that our components mapped to Pedido entity arent null
     * or dont exist in our database, following the validation we set the delivery fee taking it from
     * the restaurant and calculate the total price of our order
     * @param pedido
     * @return Pedido pedido
     */
    @Transactional
    public Pedido emitir(Pedido pedido){
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();
        return pedidoRepository.save(pedido);
    }

    /**
     * We fetch the components below to make sure they are not null or invalid.
     * After that we guarantee that the payment method is accepted and then we are
     * set to go
     * @param pedido
     */
    private void validarPedido(Pedido pedido){
        var restaurante = restauranteService.findById(pedido.getRestaurante().getId());
        var cidade = cidadeService.findById(pedido.getEnderecoEntrega().getCidade().getId());
        var cliente = usuarioService.findById(pedido.getCliente().getId());
        var formaPagamento = formaPagamentoService.findById(pedido.getFormaPagamento().getId());

        if(restaurante.naoAceitaFormaPagamento(formaPagamento)){
            throw new BusinessException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);
    }

    /**
     * Fetch all OrdemItem of the current Order, get the product based on Order's Restaurant and itself.
     * if its not null just set itself as attribute and set the unitary price for each OrderItem
     * @param pedido
     */
    private void validarItens(Pedido pedido){
        pedido.getItens().forEach(item -> {
            var produto = produtoService.findById(pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

}
