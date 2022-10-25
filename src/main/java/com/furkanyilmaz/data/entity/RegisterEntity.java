package com.furkanyilmaz.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "register")
public class RegisterEntity extends BaseEntity implements Serializable {
    public static final long serialVersionUID=1L;
    private String name;
    private String surname;
    private String email;
    private String password;

    //javada olsun ancak database bu attribute eklemesin --> @Transient
    //private String justJava;

    //büyük datalar icin kullanalım. --> @Lob
    //private Object bigData;

}
