package com.furkanyilmaz.business.services.Impl;

import com.furkanyilmaz.business.dto.UserDto;
import com.furkanyilmaz.business.services.IAuthenticationService;
import com.furkanyilmaz.security.UserPrincipal;
import com.furkanyilmaz.security.jwt.IJwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthenticationServiceImpl implements IAuthenticationService {

    //injection
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  IJwtProvider iJwtProvider;

    @Override
    public String loginReturnJwt(UserDto userDto){
        //Authentication ==> UserDto username ve password vermek
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword()));

        // UserPrincipal
        UserPrincipal  userPrincipal= (UserPrincipal) authentication.getPrincipal();

        //IJwtProvider
        return iJwtProvider.generateToken(userPrincipal);
    }
}