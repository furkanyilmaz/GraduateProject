package com.furkanyilmaz.bean;

import com.furkanyilmaz.business.dto.BlogDto;
import com.furkanyilmaz.data.entity.Blog;
import com.furkanyilmaz.data.repository.IBlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
//@Configuration
public class BlogDataBean {
//
//    private final ModelMapperBean modelMapperBean;
//
//    private final IBlogRepository repository;
//
//    @Bean
//    CommandLineRunner speedBLog(){
//        return (args)->{
//            int counter=0;
//            for (int i = 1; i <= 5; i++) {
//                UUID uuid = UUID.randomUUID();
//                BlogDto registerBlogDto = BlogDto.builder()
//                        .blogHeader(" SpringBOOT  "+ i+ ". GUN")
//                        .blogContent("bugün spring boot çalıştım, toplam kazancım "+i+ "gün. HARİKA!!")
//                        .blogImage("Image "+i)
//                        .createdDate(new Date(System.currentTimeMillis()))
//                        .build();
//                Blog registerBlogEntity = modelMapperBean.modelMapperMethod().map(registerBlogDto, Blog.class);
//                repository.save(registerBlogEntity);
//                counter++;
//            }
//            System.out.println("Başlarken "+ counter + " tane blog oluşturuldu");
//        };
//    }

}
