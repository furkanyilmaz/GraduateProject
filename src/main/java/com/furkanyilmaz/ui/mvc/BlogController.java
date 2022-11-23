package com.furkanyilmaz.ui.mvc;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.business.dto.BlogDto;
import com.furkanyilmaz.data.entity.Blog;
import com.furkanyilmaz.data.repository.IBlogRepository;
import com.furkanyilmaz.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//lombok
@RequiredArgsConstructor
@Log4j2

//Controller
@Controller
//@RequestMapping("/controller")
public class BlogController implements IBlogController {

    private final IBlogRepository repository;
    private final ModelMapperBean modelMapperBean;

    //SPEEDBLOG
    //http://localhost:1111/speedBlog
    @GetMapping("/speedBlog")
    public String createSpeedBlog(Model model) {
        int counter = 0;
        for (int i = 1; i <= 5; i++) {
            UUID uuid = UUID.randomUUID();
            Blog blogEntity = Blog.builder()
                    .blogHeader(" SpringBOOT  " + i + ". BLOG")
                    .blogContent("spring boot çalıştım, toplam kazancım " + i + "BLOG. GREAT!!")
                    .blogImage("Image " + i)
//                    .blogCreatedDate(new Date(System.currentTimeMillis()))
                    .build();
            repository.save(blogEntity);
            //model mapper kullanmadan direkt entity'i build ettik.(dto değil)
            counter++;
        }
        model.addAttribute("key_dataset", counter + " tane Blog oluşturuldu...");
        return "redirect:/blog/list";
    }

    // SPEED DELETE
    // http://localhost:1111/speedData
    @GetMapping("/speedDelete")
    public String deleteSpeedData(Model model) {
        repository.deleteAll();
        return "redirect:/blog/list";
    }

    // CREATE
    // http://localhost:1111/blog/create
    @GetMapping("/blog/create")
    public String validationGetBlog(Model model) {
        model.addAttribute("key_blog", new Blog());
        return "blog_create";
    }

    //CREATE
    // http://localhost:1111/blog/create
    @PostMapping("/blog/create")
    public String validationPostBlog(@Valid @ModelAttribute("key_blog") BlogDto blogDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("HATA: " + bindingResult);
            return "blog_create";
        }
        //eğer valiadtion bir hata yoksa
        model.addAttribute("blog_success", "Blog Kaydı Başarılı " + blogDto);
        log.info("Başarılı " + blogDto);

        //Database
        //model mapper
        Blog blogEntity = modelMapperBean.modelMapperMethod().map(blogDto, Blog.class);
        try {
            if (blogEntity != null) {
                repository.save(blogEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //File
        return "success";
    }

    // LIST
    // http://localhost:1111/blog/list
    @GetMapping("/blog/list")
    public String blogList(Model model) {
        List<Blog> list = repository.findAll();
        model.addAttribute("key_blog", list);
        list.forEach((temp) -> {
            System.out.println(temp);
        });
        return "blog_list";
    }

    // FIND
    // http://localhost:1111/blog/find
    // http://localhost:1111/blog/find/1
    @GetMapping("/blog/find/{id}")
    public String blogFindById(@PathVariable(name = "id") Long id, Model model) {
        Blog blogEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu blog kaydı yoktur.."));
        model.addAttribute("blog_find", blogEntity);
        return "blog_detail_page";
    }

    // DELETE
    // http://localhost:1111/blog/delete
    // http://localhost:1111/blog/delete/1
    @GetMapping({"/blog/delete", "/blog/delete/{id}"})
    public String blogDeleteById(@PathVariable(name = "id", required = false) Long id, Model model) {
        Blog blogEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu blog kaydı yoktur"));
        if (blogEntity != null) {
            repository.deleteById(id);
            model.addAttribute("key_delete", blogEntity + " silindi");
        } else
            model.addAttribute("key_delete", id + " numaralı veri yoktur");
        return "redirect:/blog/list";
    }

    //UPDATE
    // http://localhost:1111/blog/update/3
    @GetMapping("/blog/update/{id}")
    public String updateGetBlog(@PathVariable(name = "id") Long id, Model model) {
        Blog blogEntityFind = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id + " nolu blog kaydı yoktur"));
        if (blogEntityFind != null) {
            model.addAttribute("key_update", blogEntityFind);
        } else
            model.addAttribute("key_update", id + " numaralı blog yoktur");
        return "blog_update";
    }

    //UPDATE
    // http://localhost:1111/blog/update/
    @PostMapping("/blog/update/{id}")
    public String updatePostBlog(@PathVariable(name = "id") Long id, @Valid @ModelAttribute("key_update") BlogDto blogDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("HATA: " + bindingResult);
            return "blog_update";
        }
        Blog blogEntity = modelMapperBean.modelMapperMethod().map(blogDto, Blog.class);
        try {
            if (blogEntity != null) {
                repository.save(blogEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/blog/list";
    }

}
