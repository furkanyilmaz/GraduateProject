package com.furkanyilmaz.data.repository;

import com.furkanyilmaz.data.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBlogRepository extends JpaRepository<Blog, Long> {

    //Blog findByBlogHeader(String blogHeader); başlığın içerdiği tüm blokları listelemek isteyelim..

}
