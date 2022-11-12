package com.furkanyilmaz.business.services.Impl;

import com.furkanyilmaz.bean.ModelMapperBean;
import com.furkanyilmaz.business.dto.BlogDto;
import com.furkanyilmaz.business.services.IBlogService;
import com.furkanyilmaz.data.entity.Blog;
import com.furkanyilmaz.data.repository.IBlogRepository;
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
public class BlogServiceImpl implements IBlogService {

    //injection
    private final IBlogRepository repository; // db işlemlerimizi göreceğimiz yer.
    private final ModelMapperBean modelMapperBean;


    // Model Mapper
    @Override
    public BlogDto entityToDto(Blog blogEntity) {
        return modelMapperBean.modelMapperMethod().map(blogEntity,BlogDto.class);
    }

    @Override
    public Blog dtoToEntity(BlogDto blogDto) {
        return modelMapperBean.modelMapperMethod().map(blogDto,Blog.class);
    }

    //CREATE
    @Override
    public BlogDto createBlog(BlogDto blogDto) {
        Blog blogEntity = dtoToEntity(blogDto);
        repository.save(blogEntity);
        return blogDto;
    }

    //LIST
    @Override
    public List<BlogDto> listBlog() {
        List<Blog> blogEntityList = repository.findAll();
        List<BlogDto> blogDtoList = new ArrayList<>();
        for (Blog temp : blogEntityList){
            BlogDto entityToDto = entityToDto(temp);
            blogDtoList.add(entityToDto);
        }
        return blogDtoList;
    }

    @Override
    public BlogDto findBlog(Long id) {
        Blog blogEntity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id + "id'si bulunamadı."));
        BlogDto entityToDto = entityToDto(blogEntity);
        return entityToDto;
    }

    //DELETE
    @Override
    public Map<String, Boolean> deleteBlog(Long id) {
        Blog blogEntity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id + " id'si bulunamadı, silinemez."));
        repository.delete(blogEntity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted select blog", Boolean.TRUE);
        return response;
    }

    //UPDATE
    @Override
    public BlogDto updateBlog(Long id, BlogDto blogDto) {
        Blog blogEntity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id + "id'si bulunamadı, güncellenemez."));
        if (blogEntity != null){
            blogEntity.setBlogHeader(blogDto.getBlogHeader());
            blogEntity.setBlogContent(blogDto.getBlogContent());
            blogEntity.setBlogImage(blogDto.getBlogImage());
            repository.save(blogEntity);
        }
        return null;
    }
}
