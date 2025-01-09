package com.mysite.sbb.Service;

import com.mysite.sbb.Dto.CommentDto;
import com.mysite.sbb.Entity.Comment;
import com.mysite.sbb.Entity.Post;
import com.mysite.sbb.Repository.CommentRepository;
import com.mysite.sbb.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentDto createComment(Long postId, String content, String username) {
        // 게시물 조회
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        // 댓글 생성 및 저장
        Comment comment = Comment.builder()
                .content(content)
                .author(username)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);

        // 저장된 댓글을 DTO로 변환하여 반환
        return CommentDto.of(savedComment);
    }

    public Page<CommentDto> getCommentsByPostId(Long postId, int page){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        List<CommentDto> comments = post.getComments()
                .stream()
                .map(CommentDto::of)
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, 3);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), comments.size());

        if(start >= comments.size()){
            return new PageImpl<>(List.of(), pageable, comments.size());
        }
        return new PageImpl<>(comments.subList(start, end), pageable, comments.size());
    }

//    public List<CommentDto> getCommentsByPostId(Long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException());
//        return post.getComments().stream().map(CommentDto::of).collect(Collectors.toList());
//    }

    public void deleteComment(Long id, Long commentId, String username) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("댓글  x "));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글  x "));
        // 코멘트에 이어져있는 포스트 리스트의 아이디를(postId) 가져왔을때 현재 postId와 comment 로 찾아간 postId가 동일할 때
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new IllegalArgumentException("해당 게시물에 속하지 않는 댓글입니다.");
        }

        if (!comment.getAuthor().equals(username)) {
            throw new SecurityException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}
