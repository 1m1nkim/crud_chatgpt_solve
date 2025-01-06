package com.mysite.sbb.controller;

import com.mysite.sbb.Dto.PostDto;
import com.mysite.sbb.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//이 부분만 수정 ㅇ
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostContoller {
    private final PostService postService;

    @GetMapping("")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // ROLE_ADMIN 권한 필요
    public PostDto findById(@PathVariable("id") Long id){
        return postService.findById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public PostDto create(@RequestBody PostDto postDto){
        return postService.create(postDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public PostDto update(@PathVariable("id") Long id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public List<PostDto> searchByTitle(@PathVariable("title") String title){
        return postService.searchByTitle(title);
    }
}
