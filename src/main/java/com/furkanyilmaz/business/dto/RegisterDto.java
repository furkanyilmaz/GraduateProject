package com.furkanyilmaz.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterDto {

    private Long id;
    @NotEmpty(message = "Username cannot be null")
    private String name;
    @NotEmpty(message = "surname cannot be null")
    private String surname;

    @NotEmpty(message = "email cannot be null")
    @Email(message = "{blog.email.regex.validation.constraints.NotNull.message}")
    private String email;

    @NotEmpty(message = "{blog.password.validation.constraints.NotNull.message}")
    @Size(min=7,max = 12,message = "{blog.password.pattern.validation.constraints.NotNull.message}")
    //@Pattern(regexp = "")
    private String password;

    private Date createdDate;

}
