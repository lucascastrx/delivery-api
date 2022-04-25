package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.RestaurantNotFoundException;
import com.lucas.deliveryapi.domain.model.Restaurante;
import com.lucas.deliveryapi.domain.repository.RestauranteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class RestauranteService {

    private RestauranteRepository restauranteRepository;

    private CozinhaService cozinhaService;

    private CidadeService cidadeService;

    private FormaPagamentoService formaPagamentoService;

    private UsuarioService usuarioService;

    @Transactional
    public Restaurante addRestaurante(Restaurante restaurante){
        var cozinha = cozinhaService.findById(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);

        var cidade = cidadeService.findById(restaurante.getEndereco().getCidade().getId());
        restaurante.getEndereco().setCidade(cidade);

        return restauranteRepository.save(restaurante);
    }

    public Restaurante findById(Long id){
       return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id) );
    }

    @Transactional
    public void delete(Long id){
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (DataIntegrityViolationException e){
            throw new EntityUnviableException("Entidade está em uso");
        } catch (EmptyResultDataAccessException e){
            throw new RestaurantNotFoundException(id);
        }
    }

    @Transactional
    public void ativoState(Long id){
        var restaurante = findById(id);
        var result = restaurante.getAtivo() ? restaurante.inativar()  : restaurante.ativar();
    }

    @Transactional
    public void ativar(List<Long> restaurantes){
        restaurantes.forEach(this::ativoState);
    }

    @Transactional
    public void abertoState(Long id){
        var restaurante = findById(id);
        var result = restaurante.getAberto() ? restaurante.fechar() : restaurante.abrir();
    }

    @Transactional
    public void addOrRemovePaymentMethod(Long restauranteId, Long formaPagamentoId){
        var restaurante = findById(restauranteId);
        var formaPagamento = formaPagamentoService.findById(formaPagamentoId);

        Boolean result = restaurante.containsFormaPagamento(formaPagamento)
                ? restaurante.removeFormaPagamento(formaPagamento)
                : restaurante.addFormaPagamento(formaPagamento);
    }

    @Transactional
    public void addOrRemoveResponsavel(Long restauranteId, Long responsavelId){
        var restaurante = findById(restauranteId);
        var responsavel = usuarioService.findById(responsavelId);

        Boolean result = restaurante.containsResponsavel(responsavel)
                ? restaurante.removeResponsavel(responsavel)
                : restaurante.addResponsavel(responsavel);
    }

}
