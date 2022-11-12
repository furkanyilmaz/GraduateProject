package com.furkanyilmaz.ui.api;

import com.furkanyilmaz.business.dto.BlogDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface IBlogApi {

    //CREATE
    ResponseEntity<?> createBlog(BlogDto blogDto);

    //LIST
    ResponseEntity<?> listBlog();

    //FIND
    ResponseEntity<BlogDto> findBlog(Long id);

    //DELETE
    ResponseEntity<Map<String,Boolean>> deleteBlog(Long id);

    //UPDATE
    ResponseEntity<BlogDto> updateBlog(Long id, BlogDto blogDto);

}
