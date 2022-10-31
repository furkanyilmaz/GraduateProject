package com.furkanyilmaz.annotation;

import com.furkanyilmaz.data.entity.RegisterEntity;
import com.furkanyilmaz.data.repository.IRegisterRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor //repoyu kullanabil
public class UniqueEmailValidation implements ConstraintValidator<UserRegisterUniqueEmail,String> {
    //repoyu çağır
    private final IRegisterRepository repository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        RegisterEntity registerEntity = repository.findByEmail(email); //e mail varsa değiştir.
        if (registerEntity!=null)
            return false;
        return true;
    }
}
