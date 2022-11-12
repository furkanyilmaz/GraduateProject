package com.furkanyilmaz.ui.api.Impl;

import com.furkanyilmaz.business.services.IBlogService;
import com.furkanyilmaz.error.ApiResult;
import com.furkanyilmaz.ui.api.IBlogApi;
import com.google.gson.JsonElement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Lombok
@RequiredArgsConstructor

@RestController
@RequestMapping("gateway/blog")
public class BlogApiImpl implements IBlogApi {

    private final IBlogService blogService;
    private static final String PATH = "gateway/blog";

    //SAVE
    //http://localhost:5555/gateway/blog  ==> POST
    @Override
    @PostMapping
    public ApiResult saveBlog(@RequestBody JsonElement jsonElement) {
        blogService.blogSave(jsonElement);
        return new ApiResult(200, "Blog kaydi olusturuldu", PATH);
    }

    //LIST
    //http://localhost:5555/gateway/blog  ==> GET
    @Override
    @GetMapping
    public ResponseEntity<List<?>> listBlog() {
        blogService.blogList();
        return ResponseEntity.ok(blogService.blogList());
    }

    //FIND
    //http://localhost:5555/gateway/blog/1
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findBlog(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(blogService.blogFind(id));
    }

    //DELETE
    //http://localhost:5555/gateway/blog/1
    @Override
    @DeleteMapping("/{id}")
    public ApiResult deleteBlog(@PathVariable(name = "id") Long id) {
        blogService.blogDelete(id);
        return new ApiResult(200, "Blog silindi", PATH);
    }

    //UPDATE
    //http://localhost:5555/gateway/blog/1
    @Override
    @PutMapping("/{id}")
    public ApiResult updateBlog(@PathVariable(name = "id") Long id, @RequestBody JsonElement jsonElement) {
        blogService.blogUpdate(id,jsonElement);
        return new ApiResult(200, "Blog guncellendi", PATH);
    }
}
