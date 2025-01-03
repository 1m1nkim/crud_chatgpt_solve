package com.mysite.sbb.Repository;

import com.mysite.sbb.Entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitleContaining(String title);
    Page<Post> findByTitleContaining(String keyword, Pageable pageable);
    //Pageable은 페이지 번호, 페이지 크기, 등의 정보를 포함합니다.
    //Page<Post>는 페이징된 데이터와 관련 메타데이터(전체 페이지 수, 현재 페이지 등) 을 나타냅니다.

}
