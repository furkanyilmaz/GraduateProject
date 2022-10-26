package com.furkanyilmaz.annotation;

import com.furkanyilmaz.data.entity.RegisterEntity;
import com.furkanyilmaz.data.repository.IRegisterRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UniqueEmailValidation implements ConstraintValidator<UserRegisterUniqueEmail,String> {

    //repo
    private final IRegisterRepository repository;

    @Override
    public void initialize(UserRegisterUniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        RegisterEntity registerEntity = repository.findByEmail(email);
        if (registerEntity!=null)
            return false;
        return true;
    }
}
