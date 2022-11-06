package com.furkanyilmaz.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

//lombok
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "blog")
public class Blog extends BaseEntity implements Serializable {
    public static final long serialVersionUID=1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;

    @Column(name = "blogHeader", length = 40, nullable = false)
    private String blogHeader;

    @Column(name = "blogContent", length = 200, nullable = false)
    private String blogContent;

    private String blogImage;

//    @Column(name = "blogCreatedDate")
//    private Date createdDate = new Date(System.currentTimeMillis());
}
