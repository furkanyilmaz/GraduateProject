package com.furkanyilmaz.business.dto;

import com.furkanyilmaz.annotation.UserRegisterUniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DailyDto implements Serializable {

    private Long id;

    @NotEmpty(message = "{blog.username.validation.constraints.NotNull.message}")
    private String dailyHeader;
    @NotEmpty(message = "{blog.surname.validation.constraints.NotNull.message}")
    private String dailyContent;

    @NotEmpty(message = "{blog.email.validation.constraints.NotNull.message}")
    @Email(message = "{blog.email.regex.validation.constraints.NotNull.message}")
    @UserRegisterUniqueEmail
    private String email;

    @NotEmpty(message = "{blog.password.validation.constraints.NotNull.message}")
    //@Size(min=7,max = 12,message = "{blog.password.pattern.validation.constraints.NotNull.message}")
    //@Pattern(regexp = "")
    private String password;

    private Date createdDate;

}
