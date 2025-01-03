package com.mysite.sbb.controller;

import com.mysite.sbb.Entity.Post;
import com.mysite.sbb.Repository.PostRepository;
import com.mysite.sbb.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//이 부분만 수정 ㅇ
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostContoller {

    private final PostService postService;

    @GetMapping("")
    public List<Post> findAll(){
        return postService.getList();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id){
        return postService.findIdContent(id);
    }

    @PostMapping("")
    public Post create(@RequestBody @Valid Post post) {
        return postService.create(post); // JSON 데이터를 Post 객체로 변환 후 저장
    }


    @PutMapping("/{id}")
    public Post update(@PathVariable("id") Long id, @RequestBody @Valid Post updatedPost) {
        return postService.update(id, updatedPost);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        postService.delete(id);
    }

    @GetMapping("/title/{title}")
    public List<Post> searchTitle(@PathVariable("title") String title){
        return postService.searchTitle(title);
    }
}
