package com.furkanyilmaz.business.iservices.impl;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.bean.PasswordEncoderBean;
import com.furkanyilmaz.business.dto.RegisterDto;
import com.furkanyilmaz.business.iservices.IRegisterServices;
import com.furkanyilmaz.data.entity.RegisterEntity;
import com.furkanyilmaz.data.repository.IRegisterRepository;
import com.furkanyilmaz.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

//lombok
@RequiredArgsConstructor //for injects-- private final zorunlu. constructure'ı oluşturmayı zorunlu kılarız. 27/28/29 satır
@Log4j2

//asıl işi yapan katman..
@Service
@Transactional//işlemlerin birbirleriyle iletişimi ve takibi ?
public class RegisterServicesImpl implements IRegisterServices {

    //Injects
    private final IRegisterRepository repository; //db işlemleri
    private final ModelMapperBean modelMapperBean;
    private final PasswordEncoderBean passwordEncoderBean; //new

    // SPEED DATA
    // http://localhost:8090/speedData

    @Override
    @GetMapping("/speedData")    //(5 random data ekle)
    public String createSpeedData(Model model) {
        int counter = 0;
        for (int i = 1; i <= 5; i++) {
            UUID uuid = UUID.randomUUID();
            RegisterEntity registerEntity = RegisterEntity.builder() // new register
                    .name("adı " + i)
                    .surname("soyadı " + i)
                    .password("root").email(uuid.toString().concat("@gmail.com")).build();
            registerEntity.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(registerEntity.getPassword()));
            repository.save(registerEntity);
            counter++;
        }
        model.addAttribute("key_dataset", counter + " tane Register Entity oluşturuldu");

        return "register_list";
    }

    //CREATE
    // http://localhost:8090/register/create
    @Override
    @GetMapping("/register/create") //Create için gerekli --> url kısımları aynı
    public String validationGetRegister(Model model) {
        model.addAttribute("key_register", new RegisterDto());
        return "register_create";
    }

    //CREATE
    // http://localhost:8090/register/create
    @Override
    @PostMapping("/register/create") //Create için gerekli --> url kısımları aynı
    public String validationPostRegister(@Valid @ModelAttribute("key_register") RegisterDto registerDto, BindingResult bindingResult, Model model) { // o alana veri göndermek için.
        if (bindingResult.hasErrors()) {//hatalar olursa almak için.
            log.error("HATA: " + bindingResult);//yakala logda paylaş
            return "register_create";
        }
        //eğer validasyonda bir hata yok ise;
        //model.addAttribute("register_success", "Üye kaydı başarılı : " + registerDto.getId());
        log.info("Başarılı " + registerDto);

        //Database
        //masking password
        registerDto.setPassword(passwordEncoderBean.passwordEncoderMethod().encode(registerDto.getPassword()));
        //model mapper yapmam lazım çünkü dbo'yu entity'e çevirmek gerekli db için
        RegisterEntity registerEntity = modelMapperBean.modelMapperMethod().map(registerDto, RegisterEntity.class); //dto ver entity al, db işlemlerini yapabilelim
        //RegisterEntity registerEntity=new RegisterEntity(); //diğer yol. tek tek eşle set ve get ile.(entity-dto)
        //registerEntity.setId(registerDto.getId());
        //registerEntity.setName(registerDto.getName());
        //registerEntity.setSurname(registerDto.getSurname());
        //registerEntity.setEmail(registerDto.getEmail());
        //registerEntity.setPassword(registerDto.getPassword());
        try {
            if (registerEntity != null) {
                repository.save(registerEntity); //içi doluysa dbye kaydet
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //File
        return "redirect:/register/list";
    }

    //LIST
    // http://localhost:8090/register/list
    @Override
    @GetMapping("/register/list")
    public String registerList(Model model) { // model ; html tarafı için
        List<RegisterEntity> list = repository.findAll();
        model.addAttribute("register_list", list);
        list.forEach((temp) -> {
            System.out.println(temp);
        });
        return "register_list";
    }

    //FIND
    // http://localhost:8090/register/find
    // http://localhost:8090/register/find/1
    @Override
    @GetMapping({"/register/find/{id}"})
    public String registerFindById(@PathVariable(name = "id") Long id, Model model) {
        RegisterEntity registerEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu kayıt yoktur"));
        model.addAttribute("register_find", registerEntity); //ilgili datayı göster
        return "register_detail_page";
    }

    //DELETE
    // http://localhost:8090/register/delete
    // http://localhost:8090/register/delete/1
    @Override
    @GetMapping({"/register/delete", "/register/delete/{id}"})
    public String registerDeleteById(@PathVariable(name = "id", required = false) Long id, Model model) { // model yapısında gösteririz bu datayı
        RegisterEntity registerEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu kayıt yoktur"));
        if (registerEntity != null) {
            repository.delete(registerEntity);
            model.addAttribute("key_delete", registerEntity + " silindi...");
        } else model.addAttribute("key_delete", id + " numaralı veri yok.");

        return "redirect:/register/list";
    }

    //UPDATE-GET
    // http://localhost:8090/register/update
    @Override
   // @GetMapping("/register/update/{id}") //Create için gerekli --> url kısımları aynı
    public String updateGetRegister(@PathVariable(name = "id") Long id, Model model) {
        RegisterEntity registerEntityFind = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu kayıt yoktur"));
        if (registerEntityFind != null) {
            model.addAttribute("key_update", registerEntityFind);
        } else
            model.addAttribute("key_update", id + " numaralı veri yok.");
        return "register_update";
    }

    //UPDATE-POST
    // http://localhost:8090/register
    @Override
    public RegisterDto updatePostRegister(Long id, RegisterDto registerDto) {
        RegisterEntity registerEntity = modelMapperBean.modelMapperMethod().map(registerDto, RegisterEntity.class);
        try {
            if (registerEntity != null) {
                repository.save(registerEntity);
                //model.addAttribute("key_update", "Güncelleme tamam");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registerDto;
    }
}