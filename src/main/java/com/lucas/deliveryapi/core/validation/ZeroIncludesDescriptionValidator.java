package com.lucas.deliveryapi.core.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class ZeroIncludesDescriptionValidator implements ConstraintValidator <ZeroIncludesDescription, Object>{
    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;


    @Override
    public void initialize(ZeroIncludesDescription constraint) {
        this.valorField = constraint.valorField();
        this.descricaoField = constraint.descricaoField();
        this.descricaoObrigatoria = constraint.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext context) {
        boolean valid = true;

        try {
            BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(o.getClass(), valorField)
                    .getReadMethod()
                    .invoke(o);
            String descricao = (String) BeanUtils.getPropertyDescriptor(o.getClass(), valorField)
                    .getReadMethod()
                    .invoke(o);

            if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
                valid = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
            }
            return valid;
        } catch (Exception e) {
            throw new ValidationException(e);
        }
    }
}
