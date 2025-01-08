package com.mysite.sbb.controller;

import com.mysite.sbb.Dto.CommentDto;
import com.mysite.sbb.Dto.PostDto;
import com.mysite.sbb.Service.CommentService;
import com.mysite.sbb.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostContoller {
    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public Page<PostDto> getAllPosts(@RequestParam(name = "page", defaultValue = "0") int page){
        return postService.getAllPosts(page);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // ROLE_ADMIN 권한 필요
    public PostDto findById(@PathVariable("id") Long id){
        return postService.findById(id);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public PostDto create(@RequestBody PostDto postDto, Principal principal){
        String username = principal.getName();
        return postService.create(postDto, username);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public PostDto update(@PathVariable("id") Long id, @RequestBody PostDto postDto, Principal principal) {
        String username = principal.getName(); // 현재 로그인된 사용자 이름
        return postService.update(id, postDto, username);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasRole('USER')") // ROLE_USER 권한 필요
    public List<PostDto> searchByTitle(@PathVariable("title") String title){
        return postService.searchByTitle(title);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDto> getPostComments(@PathVariable Long id){
        return commentService.getCommentsByPostId(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public void delete(@PathVariable("id") Long id, Principal principal){
        String username = principal.getName();
        postService.delete(id, username);
    }
}
