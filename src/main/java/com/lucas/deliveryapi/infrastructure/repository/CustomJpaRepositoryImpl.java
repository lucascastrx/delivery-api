package com.lucas.deliveryapi.infrastructure.repository;

import com.lucas.deliveryapi.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;


public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID>
        implements CustomJpaRepository<T, ID> {

    private EntityManager em;

    public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation
                                    , EntityManager entityManager) {
        super(entityInformation, entityManager);

        em = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();

       T entity = em.createQuery(jpql, getDomainClass())
                .setMaxResults(1)
                .getSingleResult();

        return Optional.ofNullable(entity);
    }

    @Override
    public void detach(T entity) {
        em.detach(entity);
    }
}
