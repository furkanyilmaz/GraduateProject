package com.furkanyilmaz.bean;

import com.furkanyilmaz.business.dto.DailyDto;
import com.furkanyilmaz.data.entity.DailyEntity;
import com.furkanyilmaz.data.repository.IDailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@Configuration
public class DailyDataBean {
//
//    private final ModelMapperBean modelMapperBean;
//    private final PasswordEncoderBean passwordEncoderBean;
//
//    @Bean//commandlineRunner başlarken çalışmasını isteriz..
//    CommandLineRunner speedRegister(IDailyRepository repository){ //private final
//        return (args)->{
//            int counter=0;
//            for (int i = 1; i <= 5; i++) {
//                UUID uuid = UUID.randomUUID();
//                DailyDto registerDto = DailyDto.builder()
//                        .email(uuid.toString().concat(" @gmail.com"))
//                        .password(passwordEncoderBean.passwordEncoderMethod().encode("root"+i))
//                        .dailyHeader("adi"+i)
//                        .dailyContent("soyadi"+i)
//                        .createdDate(new Date(System.currentTimeMillis()))
//                        .build();
//                DailyEntity registerEntity = modelMapperBean.modelMapperMethod().map(registerDto, DailyEntity.class);
//                repository.save(registerEntity);
//                counter++;
//            }
//            System.out.println("Başlarken "+ counter + " tane veri oluşturuldu");
//        };
//    }
}