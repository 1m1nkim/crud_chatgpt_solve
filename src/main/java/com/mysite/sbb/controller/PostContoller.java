package com.mysite.sbb.controller;

import com.mysite.sbb.Dto.PostDto;
import com.mysite.sbb.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//이 부분만 수정 ㅇ
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostContoller {

    private final PostService postService;

    @GetMapping("")
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDto findById(@PathVariable("id") Long id){
        return postService.findById(id);
    }

    @PostMapping("")
    public PostDto create(@RequestBody PostDto postDto){
        return postService.create(postDto);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable("id") Long id, @RequestBody PostDto postDto){
        return postService.update(id, postDto);
    }

    @GetMapping("/title/{title}")
    public List<PostDto> searchByTitle(@PathVariable("title") String title){
        return  postService.searchByTitle(title);
    }
}
