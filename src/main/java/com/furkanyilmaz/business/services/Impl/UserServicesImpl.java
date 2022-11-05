package com.furkanyilmaz.business.services.Impl;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.bean.PasswordEncoderBean;
import com.furkanyilmaz.business.dto.UserDto;
import com.furkanyilmaz.business.services.IUserServices;
import com.furkanyilmaz.data.entity.UserEntity;
import com.furkanyilmaz.data.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//lombok
@RequiredArgsConstructor
@Log4j2

@Service
@Transactional
//asıl işi yapan yer
public class UserServicesImpl implements IUserServices {

    //injection
    private final IUserRepository repository;
    private final ModelMapperBean modelMapperBean;
    private final PasswordEncoderBean passwordEncoderBean;

    //Model Mapper
    @Override
    public UserDto EntityToDto(UserEntity userEntity) {
        return modelMapperBean.modelMapperMethod().map(userEntity,UserDto.class);
    }

    @Override
    public UserEntity DtoToEntity(UserDto userDto) {
        return modelMapperBean.modelMapperMethod().map(userDto,UserEntity.class);
    }

    //CREATE
    @Override
    public UserDto createUser(UserDto userDto) {
        if(userDto!=null){
            userDto.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(userDto.getPassword()));
            UserEntity userEntity=DtoToEntity(userDto);
            repository.save(userEntity);
        }
        return userDto;
    }

    //LIST
    @Override
    public List<UserDto> getAllUser() {
        Iterable<UserEntity>  userEntityList= repository.findAll();
        //dto List
        List<UserDto> dtoList=new ArrayList<>();
        for (UserEntity temp:  userEntityList) {
            UserDto userDto=EntityToDto(temp);
            dtoList.add(userDto);
        }
        return dtoList;
    }

    //FIND
    @Override
    public Optional<UserEntity> findUsername(String username) {
        return repository.findByUsername(username);
//        UserEntity userEntity = repository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException(username+ " kullanıcı bulunamadı."));
//        UserDto EntityToDto = ?????????????????????api nasıl olacak????
//        return null;
    }
}