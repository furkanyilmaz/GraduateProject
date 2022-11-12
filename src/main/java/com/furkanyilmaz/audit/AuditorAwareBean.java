package com.furkanyilmaz.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorAwareBean {

    @Bean
    public AuditorAware<String> auditorAwareMethod(){
        return new AuditorAwareImpl();
    }
}
