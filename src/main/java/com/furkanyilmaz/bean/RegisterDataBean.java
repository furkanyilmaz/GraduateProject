package com.furkanyilmaz.bean;

import com.furkanyilmaz.business.dto.RegisterDto;
import com.furkanyilmaz.data.entity.RegisterEntity;
import com.furkanyilmaz.data.repository.IRegisterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Configuration
public class RegisterDataBean {

    private final ModelMapperBean modelMapperBean;
    private final PasswordEncoderBean passwordEncoderBean;

    @Bean//commandlineRunner başlarken çalışmasını isteriz..
    CommandLineRunner speedRegister(IRegisterRepository repository){ //private final
        return (args)->{
            int counter=0;
            for (int i = 1; i <= 5; i++) {
                UUID uuid = UUID.randomUUID();
                RegisterDto registerDto = RegisterDto.builder()
                        .email(uuid.toString().concat(" @gmail.com"))
                        .password(passwordEncoderBean.passwordEncoderMethod().encode("root"+i))
                        .name("adi"+i)
                        .surname("soyadi"+i)
                        .createdDate(new Date(System.currentTimeMillis()))
                        .build();
                RegisterEntity registerEntity = modelMapperBean.modelMapperMethod().map(registerDto, RegisterEntity.class);
                repository.save(registerEntity);
                counter++;
            }
            System.out.println("Başlarken "+ counter + " tane veri oluşturuldu");
        };
    }
}