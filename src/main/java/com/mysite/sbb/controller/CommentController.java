package com.mysite.sbb.controller;

import com.mysite.sbb.Dto.CommentDto;
import com.mysite.sbb.Entity.Comment;
import com.mysite.sbb.Service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    @PreAuthorize("hasRole('USER')")
    public CommentDto createComment(
            @PathVariable("postId") Long postId,
            @RequestBody CommentDto commentDto,
            Principal principal) {
        String username = principal.getName(); // 현재 로그인한 사용자의 이름 가져오기
        return commentService.createComment(postId, commentDto.getContent(), username); // 서비스 호출
    }


    @GetMapping("/{postId}")
    public Page<CommentDto> getComments(@PathVariable("postId") Long postId, @RequestParam(value = "page", defaultValue = "0") int page){
        return commentService.getCommentsByPostId(postId, page);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @PreAuthorize("hasRole('USER')")
    public void deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId,Principal principal){
        String username = principal.getName();
        commentService.deleteComment(postId, commentId, username);
    }
}
