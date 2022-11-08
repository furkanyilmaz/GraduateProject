package com.furkanyilmaz.business.services.impl;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.bean.PasswordEncoderBean;
import com.furkanyilmaz.business.dto.DailyDto;
import com.furkanyilmaz.business.services.IDailyServices;
import com.furkanyilmaz.data.entity.DailyEntity;
import com.furkanyilmaz.data.repository.IDailyRepository;
import com.furkanyilmaz.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//lombok
@RequiredArgsConstructor
@Log4j2

@Service
@Transactional
//asıl iş katmanı olan yer
public class DailyServicesImpl implements IDailyServices {

    //injection
    private final IDailyRepository repository; // db işlemlerimizi göreceğimiz yer.
    private final ModelMapperBean modelMapperBean;
    private final PasswordEncoderBean passwordEncoderBean;

    // Model Mapper
    @Override
    public DailyDto entityToDto(DailyEntity dailyEntity) {
        return modelMapperBean.modelMapperMethod().map(dailyEntity, DailyDto.class);
    }

    @Override
    public DailyEntity dtoToEntity(DailyDto dailyDto) {
        return modelMapperBean.modelMapperMethod().map(dailyDto, DailyEntity.class);
    }

    //CREATE
    @Override
    public DailyDto createDaily(DailyDto dailyDto) {
        dailyDto.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(dailyDto.getPassword()));
        DailyEntity registerEntity = dtoToEntity(dailyDto);
        repository.save(registerEntity);
        return dailyDto;
    }

    //LIST
    @Override
    public List<DailyDto> listDaily() { //!!!!!!!
        List<DailyEntity> dailyEntityList = repository.findAll();//hepsini listeye at
        List<DailyDto> dtoList = new ArrayList<>();
        for (DailyEntity temp : dailyEntityList) {
            DailyDto entityToDto = entityToDto(temp);
            dtoList.add(entityToDto);
        }
        return dtoList;
    }

    //FIND
    @Override
    public DailyDto findDaily(Long id) {
        DailyEntity dailyEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " id bulunamadı"));
        DailyDto entityToDto = entityToDto(dailyEntity);
        return entityToDto;
    }

    //DELETE
    @Override
    public Map<String, Boolean> deleteDaily(Long id) {
        DailyEntity dailyEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " id bulunamadı"));
        repository.delete(dailyEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }


    //UPDATE
    @Override
    public DailyDto updateDaily(Long id, DailyDto registerDto) {
        DailyEntity dailyEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " id bulunamadı"));
        if (dailyEntity != null) {
            dailyEntity.setDailyHeader(dailyEntity.getDailyHeader());
            dailyEntity.setDailyContent(dailyEntity.getDailyContent());
            dailyEntity.setEmail(dailyEntity.getEmail());
            dailyEntity.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(dailyEntity.getPassword()));
            repository.save(dailyEntity);
        }
        return registerDto;
    }
}
