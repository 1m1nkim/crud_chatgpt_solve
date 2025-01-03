package com.mysite.sbb.Repository;

import com.mysite.sbb.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@org.springframework.stereotype.Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
