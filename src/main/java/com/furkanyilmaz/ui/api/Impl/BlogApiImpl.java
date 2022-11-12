package com.furkanyilmaz.ui.api.Impl;

import com.furkanyilmaz.business.dto.BlogDto;
import com.furkanyilmaz.business.services.IBlogService;
import com.furkanyilmaz.ui.api.IBlogApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//lombok
@RequiredArgsConstructor
@Log4j2

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class BlogApiImpl implements IBlogApi {

    //injection services
    private final IBlogService services;

    //http://localhost:1111/api/v1/blog/create
    //CREATE
    @Override
    @PostMapping("/blog/create")
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogDto blogDto) {
        services.createBlog(blogDto);
        return ResponseEntity.ok(blogDto);
    }

    //http://localhost:1111/api/v1/blog/list
    //LIST
    @Override
    @GetMapping("/blog/list")
    public ResponseEntity<?> listBlog() {
        return ResponseEntity.ok(services.listBlog());
    }

    //http://localhost:1111/api/v1/blog/find/1
    //FIND
    @Override
    @GetMapping("/blog/find/{id}")
    public ResponseEntity<BlogDto> findBlog(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(services.findBlog(id));
    }

    //http://localhost:1111/api/v1/blog/delete/2
    //DELETE
    @Override
    @DeleteMapping("/blog/delete/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBlog(@PathVariable(name = "id") Long id) {
        services.deleteBlog(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted blog", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

    //http://localhost:1111/api/v1/blog/update/3
    //UPDATE
    @Override
    @PutMapping("/blog/update/{id}")
    public ResponseEntity<BlogDto> updateBlog(@PathVariable(name = "id") Long id,@Valid @RequestBody BlogDto blogDto) {
        services.updateBlog(id, blogDto);
        return ResponseEntity.ok(blogDto);
    }
}
