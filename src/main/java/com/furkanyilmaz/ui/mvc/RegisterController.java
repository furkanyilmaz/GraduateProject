package com.furkanyilmaz.ui.mvc;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.bean.PasswordEncoderBean;
import com.furkanyilmaz.business.dto.RegisterDto;
import com.furkanyilmaz.data.entity.RegisterEntity;
import com.furkanyilmaz.data.repository.IRegisterRepository;
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

@RequiredArgsConstructor//for injects
@Log4j2

@Controller
public class RegisterController {

    //Inject
    private final ModelMapperBean modelMapperBean;
    private final IRegisterRepository repository;
    private final PasswordEncoderBean passwordEncoderBean;

    //CREATE
    // http://localhost:8090/validation/register
    @GetMapping("/validation/register")
    public String validationRegister(Model model){
        model.addAttribute("key_register", new RegisterDto());
        return "register";
    }

    //CREATE
    // http://localhost:8090/validation/register
    @PostMapping("/validation/register")
    public String validationPostRegister(@Valid @ModelAttribute("key_register") RegisterDto registerDto,
            BindingResult bindingResult, Model model ){
        if (bindingResult.hasErrors()){
            log.error("HATA: "+ bindingResult);
            return "register";
        }
        //eğer validasyonda bir hata yok ise;
        model.addAttribute("register_success", "Üye kaydı başarılı"+ registerDto);
        log.info("Başarılı "+registerDto);

        //Database
        //masking password
        registerDto.setPassword( passwordEncoderBean.passwordEncoderMethod().encode(registerDto.getPassword()));
        //model mapper
        RegisterEntity registerEntity=modelMapperBean.modelMapperMethod().map(registerDto,RegisterEntity.class);
        //RegisterEntity registerEntity=new RegisterEntity();
        //registerEntity.setId(registerDto.getId());
        //registerEntity.setName(registerDto.getName());
        //registerEntity.setSurname(registerDto.getSurname());
        //registerEntity.setEmail(registerDto.getEmail());
        //registerEntity.setPassword(registerDto.getPassword());
        try {
            if(registerEntity!=null){
                repository.save(registerEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //File
        return "success";
    }

    // SPEED DATA
    // http://localhost:8090/speedData
    @GetMapping("/speedData")
    public String createSpeedData(Model model){
        int counter=0;
        for (int i = 1; i <=8 ; i++) {
            UUID uuid=UUID.randomUUID();
            RegisterEntity registerEntity=RegisterEntity.builder()
                    .name("adı "+i)
                    .surname("soyadı "+i)
                    .password("root").email(uuid.toString().concat("@gmail.com")).build();
            repository.save(registerEntity);
            counter++;
        }
        model.addAttribute("key_dataset",counter+ " tane Register Entity oluşturuldu");
        return "list";
    }

    //LIST
    // http://localhost:8090/register/list
    @GetMapping("register/list")
    public String registerList(Model model){
        List<RegisterEntity> list= repository.findAll();
        model.addAttribute("register_list",list);
        list.forEach((temp)->{
            System.out.println(temp);
        });
        return "list";
    }

    //FIND
    // http://localhost:8090/register/find
    // http://localhost:8090/register/find/1
    @GetMapping({"register/find","register/find/{id}"})
    public String registerFindById(@PathVariable(name="id",required = false) Long id, Model model){
        RegisterEntity registerEntity=repository.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" nolu kayıt yoktur"));
        model.addAttribute("register_find",registerEntity);
        return "list";
    }

    //DELETE
    // http://localhost:8090/register/find
    // http://localhost:8090/register/find/1
    @DeleteMapping({"register/delete","register/delete/{id}"})
    public String registerDeleteById(@PathVariable(name="id",required = false) Long id,Model model){
        RegisterEntity registerEntity=repository.findById(id).orElseThrow(()->new ResourceNotFoundException(id+" nolu kayıt yoktur"));
        repository.delete(registerEntity);
        return "list";
    }
}
