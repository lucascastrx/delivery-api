package com.lucas.deliveryapi.infrastructure.repository;

import com.lucas.deliveryapi.domain.model.Restaurante;
import com.lucas.deliveryapi.domain.repository.RestauranteRepository;
import com.lucas.deliveryapi.domain.repository.RestauranteRepositoryCustomized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.lucas.deliveryapi.infrastructure.repository.spec.RestaurantesSpecs.comFreteGratis;
import static com.lucas.deliveryapi.infrastructure.repository.spec.RestaurantesSpecs.comNomeSemelhante;

/**
 * Classe que pode ser criada separadamente caso haja a necessidade de realizar consultas que envolvam código Java
 * além de jpql ou sql
 */
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryCustomized {

    @PersistenceContext
    private EntityManager em;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        /*var jpql = new StringBuilder();
        jpql.append("from Restaurante where 0 = 0 ");

        var parametros = new HashMap<String, Object>();

        if(!nome.isBlank()){
            jpql.append("and nome like :nome ");
            parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaInicial", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFinal", taxaFreteFinal);
        }

        TypedQuery<Restaurante> queries = em.createQuery(jpql.toString(), Restaurante.class);

             parametros.forEach((k,v) -> queries.setParameter(k, v) );*/

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();




        if(StringUtils.hasText(nome)){
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }


        criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query =  em.createQuery(criteria);
        return query.getResultList();

    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }
}
