package com.mysite.sbb.Service;

import com.mysite.sbb.Dto.PostDto;
import com.mysite.sbb.Entity.Post;
import com.mysite.sbb.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    //전체 리스트
    public List<PostDto> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return PostDto.fromEntity(posts);
    }

    //Id 검색 리스트
    public PostDto findById(Long id){
        Post post = postRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("게시물 존재 x"));
        return PostDto.of(post);    //여기서 of를 사용하는 이유는
                                    //빌더 패턴이기 때문에 id를 통해 검색할 수 있음
    }

    //게시글 생성
    public PostDto create(PostDto postDto) {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        Post savedPost = postRepository.save(post);
        return PostDto.of(savedPost); // 저장된 엔티티 → DTO 변환
    }

    public PostDto update(Long id, PostDto postDto){
        Post post = postRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("게시물이 존재 x"));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
        return PostDto.of(updatePost);
    }

    public List<PostDto> searchByTitle(String title){
        List<Post> posts = postRepository.findByTitleContaining(title);
        return PostDto.fromEntity(posts);
    }
}
