package com.furkanyilmaz.data.repository;

import com.furkanyilmaz.data.entity.DailyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDailyRepository extends JpaRepository<DailyEntity, Long> {
        DailyEntity findByEmail(String email);
}
