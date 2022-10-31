package com.furkanyilmaz.ui.mvc;

import com.furkanyilmaz.business.dto.RegisterDto;
import com.furkanyilmaz.business.iservices.IRegisterServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@RequiredArgsConstructor //for injects-- private final zorunlu. constructure'ı oluşturmayı zorunlu kılarız. 27/28/29 satır
@Log4j2

@Controller
//@RequestMapping("/controller")
public class RegisterController implements IRegisterController {
    //@Service

    //Inject
    private final IRegisterServices services;

    // SPEED DATA
    // http://localhost:8090/speedData

    @Override
    @GetMapping("/speedData")    //(8 random data ekle)
    public String createSpeedData(Model model){
        services.createSpeedData(model);
        return "register_list";
    }

    //CREATE
    // http://localhost:8090/register/create
    @Override
    @GetMapping("/register/create") //Create için gerekli --> url kısımları aynı
    public String validationGetRegister(Model model){
        services.validationGetRegister(model);
        return "register_create";
    }

    //CREATE
    // http://localhost:8090/register/create
    @Override
    @PostMapping("/register/create") //Create için gerekli --> url kısımları aynı
    public String validationPostRegister(RegisterDto registerDto, BindingResult bindingResult, Model model ){ // o alana veri göndermek için.
        services.validationPostRegister(registerDto,bindingResult,model);
        model.addAttribute("register_success", "Üye Kaydı Başarılı " + registerDto.getId());
        return "redirect:/register/list";
    }

    //LIST
    // http://localhost:8090/register/list
    @Override
    @GetMapping("/register/list")
    public String registerList(Model model){ // model ; html tarafı için
       services.registerList(model);
        return "register_list";
    }

    //FIND
    // http://localhost:8090/register/find
    // http://localhost:8090/register/find/1
    @Override
    @GetMapping({"/register/find/{id}"})
    public String registerFindById(@PathVariable(name="id") Long id, Model model){
        services.registerFindById(id,model);
        return "register_detail_page";
    }

    //DELETE
    // http://localhost:8090/register/delete
    // http://localhost:8090/register/delete/1
    @Override
    @GetMapping({"/register/delete","/register/delete/{id}"})
    public String registerDeleteById(@PathVariable(name="id",required = false) Long id,Model model){ // model yapısında gösteririz bu datayı
       services.registerDeleteById(id,model);
        return "redirect:/register/list";
    }

    //UPDATE-GET
    // http://localhost:8090/register/update
    @Override
    @GetMapping("/register/update/{id}") //Create için gerekli --> url kısımları aynı
    public String updateGetRegister(@PathVariable(name = "id") Long id, Model model){
       services.updateGetRegister(id,model);
        return "register_update";
    }

    //UPDATE-POST
    // http://localhost:8090/register
    @Override
    @PostMapping("/register/update/{id}")
    public String updatePostRegister(@PathVariable(name = "id") Long id, RegisterDto registerDto, BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()) {
            log.error("HATA: " + bindingResult);
            return "register_update";
        }
        services.updatePostRegister(id, registerDto);
        model.addAttribute("rename",registerDto.getName()); //??
        return "redirect:/register/list";
    }
}
