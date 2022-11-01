package com.furkanyilmaz.ui.mvc;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.bean.PasswordEncoderBean;
import com.furkanyilmaz.business.dto.DailyDto;
import com.furkanyilmaz.data.entity.DailyEntity;
import com.furkanyilmaz.data.repository.IDailyRepository;
import com.furkanyilmaz.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//lombok
@RequiredArgsConstructor
@Log4j2

//Controller
@Controller
//@RequestMapping("/controller")
public class DailyController {
    //thymeleaf CRUD
    //@Service
    //postman

    //Inject
    private final IDailyRepository repository;
    private final ModelMapperBean modelMapperBean;
    private final PasswordEncoderBean passwordEncoderBean;

    // SPEED DATA
    // http://localhost:8080/speedData
    @GetMapping("/speedData")
    public String createSpeedData(Model model) {
        int counter = 0;
        for (int i = 1; i <= 5; i++) {
            UUID uuid = UUID.randomUUID();
            DailyEntity registerEntity = DailyEntity.builder()
                    .dailyHeader("adı " + i)
                    .dailyContent("root").email(uuid.toString().concat("@gmail.com")).build();
            repository.save(registerEntity);
            counter++;
        }
        model.addAttribute("key_dataset", counter + " tane daily Entity oluşturuldu");
        return "daily_list";
    }

    // CREATE 2497-2588
    // http://localhost:8080/daily/create
    @GetMapping("/daily/create")
    public String validationGetDaily(Model model) {
        model.addAttribute("key_daily", new DailyDto());
        return "daily_create";
    }

    //CREATE
    // http://localhost:8080/daily/create
    @PostMapping("/daily/create")
    public String validationPostDaily(@Valid @ModelAttribute("key_dailyr") DailyDto dailyDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("HATA: " + bindingResult);
            return "daily_create";
        }
        //eğer valiadtion bir hata yoksa
        model.addAttribute("daily_success", "Üye Kaydı Başarılı " + dailyDto);
        log.info("Başarılı " + dailyDto);

        //Database
        //masking password
        dailyDto.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(dailyDto.getPassword()));

        //model mapper
        DailyEntity registerEntity = modelMapperBean.modelMapperMethod().map(dailyDto, DailyEntity.class);
        //model mapper yerine biz yazarsak
        //RegisterEntity registerEntity=new RegisterEntity();
        //registerEntity.setId(registerDto.getId());
        //registerEntity.setName(registerDto.getName());
        //registerEntity.setSurname(registerDto.getSurname());
        //registerEntity.setEmail(registerDto.getEmail());
        //registerEntity.setPassword(registerDto.getPassword());
        try {
            if (registerEntity != null) {
                repository.save(registerEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //File
        return "success";
    }


    // LIST
    // http://localhost:8080/daily/list
    @GetMapping("/daily/list")
    public String dailyList(Model model) {
        List<DailyEntity> list = repository.findAll();
        model.addAttribute("daily_list", list);
        list.forEach((temp) -> {
            System.out.println(temp);
        });
        return "daily_list";
    }

    // FIND
    // http://localhost:8080/daily/find
    // http://localhost:8080/daily/find/1
    @GetMapping( "/daily/find/{id}")
    public String dailyFindById(@PathVariable(name = "id") Long id, Model model) {
        //1.YOL
        /*Optional<RegisterEntity> findData = repository.findById(id);
        if (findData.isPresent()) {
            return "Data: " + findData.get();
        } else {
            return id + " numaralı Data: Bulunamadı  ";
        }*/

        //2.YOL
        DailyEntity registerEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu kayıt yoktur"));
        model.addAttribute("daily_find", registerEntity);
        return "daily_detail_page";
    }

    // DELETE
    // http://localhost:8080/daily/delete
    // http://localhost:8080/daily/delete/1
    @GetMapping({"/daily/delete", "/daily/delete/{id}"})
    public String dailyDeleteById(@PathVariable(name = "id", required = false) Long id, Model model) {
        DailyEntity registerEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu kayıt yoktur"));
        if (registerEntity != null) {
            repository.deleteById(id);
            model.addAttribute("key_delete", registerEntity + " silindi");
        } else
            model.addAttribute("key_delete", id + " numaralı veri yoktur");
        return "redirect:/daily/list";
    }

    //UPDATE
    // http://localhost:8080/update/daily
    @GetMapping("/daily/update/{id}")
    public String updateGetDaily(@PathVariable(name = "id") Long id, Model model) {
        DailyEntity registerEntityFind = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu kayıt yoktur"));
        if (registerEntityFind != null) {
            model.addAttribute("key_update", registerEntityFind);
        } else
            model.addAttribute("key_update", id + " numaralı veri yoktur");
        return "daily_update";
    }

    //UPDATE
    // http://localhost:8080/update/daily
    @PostMapping("/daily/update/{id}")
    public String updatePostDaily(@PathVariable(name = "id") Long id, @Valid @ModelAttribute("key_update") DailyDto dailyDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("HATA: " + bindingResult);
            return "daily_update";
        }
        DailyEntity registerEntity = modelMapperBean.modelMapperMethod().map(dailyDto, DailyEntity.class);
        try {
            if (registerEntity != null) {
                repository.save(registerEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/daily/list";
    }
}