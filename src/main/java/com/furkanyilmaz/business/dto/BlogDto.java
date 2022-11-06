package com.furkanyilmaz.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Log4j2
public class BlogDto implements Serializable {

    private Long id;

    @NotEmpty(message = "{blog.header.validation.constraints.NotNull.message}")
    private String blogHeader;

    @NotEmpty(message = "{blog.content.validation.constraints.NotNull.message}")
    private String blogContent;

    private String blogImage;

    private Date blogCreatedDate;
}
