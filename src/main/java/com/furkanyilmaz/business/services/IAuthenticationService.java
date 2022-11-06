package com.furkanyilmaz.business.services;

import com.furkanyilmaz.business.dto.UserDto;

public interface IAuthenticationService {

    String loginReturnJwt(UserDto userDto);
}
