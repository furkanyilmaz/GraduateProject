package com.furkanyilmaz.business.services;

import com.furkanyilmaz.business.dto.BlogDto;
import com.furkanyilmaz.data.entity.Blog;

import java.util.List;
import java.util.Map;

public interface IBlogService {

    BlogDto entityToDto(Blog blogEntity);

    Blog dtoToEntity(BlogDto blogDto);

    //CREATE
    BlogDto createBlog(BlogDto blogDto);

    //LIST
    List<BlogDto> listBlog();

    //FIND
    BlogDto findBlog(Long id);

    //DELETE
    Map<String,Boolean> deleteBlog(Long id);

    //UPDATE
    BlogDto updateBlog(Long id, BlogDto blogDto);
}
