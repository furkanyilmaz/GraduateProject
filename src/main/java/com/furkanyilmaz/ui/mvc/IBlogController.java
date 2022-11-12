package com.furkanyilmaz.ui.mvc;

import com.furkanyilmaz.business.dto.BlogDto;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;

public interface IBlogController {

    public String createSpeedBlog(Model model);

    public String deleteSpeedData(Model model);

    public String validationGetBlog(Model model);

    public String validationPostBlog(BlogDto blogDto, BindingResult bindingResult, Model model);

    public String blogList(Model model);

    public String blogFindById(Long id, Model model);

    public String blogDeleteById(Long id, Model model);

    public String updateGetBlog(Long id, Model model);

    public String updatePostBlog(Long id,BlogDto dailyDto, BindingResult bindingResult, Model model);

}
