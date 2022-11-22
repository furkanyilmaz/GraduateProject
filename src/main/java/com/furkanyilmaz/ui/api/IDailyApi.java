package com.furkanyilmaz.ui.api;

import com.furkanyilmaz.business.dto.DailyDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IDailyApi {
    //CREATE
    ResponseEntity<?> createDaily(DailyDto dailyDto);

    //LIST
    ResponseEntity<?> listDaily();

    //FIND
    ResponseEntity<DailyDto> findDaily(Long id);

    //DELETE
    ResponseEntity <Map<String, Boolean>> deleteDaily( Long id);

    //UPDATE
    ResponseEntity<DailyDto>  updateDaily(Long id, DailyDto registerDto);
}
