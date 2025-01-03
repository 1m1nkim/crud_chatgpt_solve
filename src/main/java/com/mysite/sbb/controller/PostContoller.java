package com.mysite.sbb.controller;

import com.mysite.sbb.Entity.Post;
import com.mysite.sbb.Repository.PostRepository;
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

    private final PostRepository postRepository;

    @GetMapping("")
    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id){
        //@PathVariable은 경로변수를 자동으로 매핑해준다
        //즉 http://localhost:8080/api/post/id=1 이런식으로 쿼리 파라미터 형식으로 매핑해주는 것이 아닌
        //http://localhost:8080/api/post/1 이런식으로 경로 변수를 매핑해줘야한다.
        Optional<Post> op = postRepository.findById(id);
        Post a = op.get();
        return a.getContent();
    }

    // post랑 put을 간단하게 넣기 위해 json으로 넣을 땐  @RequestBody이 자동으로 JSON을 Post Entity로 인식할 수 있음
//    @PostMapping("")
//    public void create(String title, String content){
//        Post a = new Post();
//        a.setTitle(title);
//        a.setContent(content);
//        postRepository.save(a);
//    }
       //즉 이 상황에서는 Json을 하나씩 넣는 것 보단 RequestBody를 사용하면 Json을 객체로 받을 수 있다.
    @PostMapping("")
    public Post create(@RequestBody Post post) {
        return postRepository.save(post); // JSON 데이터를 Post 객체로 변환 후 저장
    }

//    @PutMapping("/{id}")
//    public void update(@PathVariable("id") Long id, String title, String content){
//        Optional<Post> op = postRepository.findById(id);
//        Post a = op.get();
//        a.setTitle(title);
//        a.setContent(content);
//        postRepository.save(a);
//    }

    @PutMapping("/{id}")
    public Post update(@PathVariable("id") Long id, @RequestBody Post updatedPost) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        return postRepository.save(post);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        Optional<Post> op = postRepository.findById(id);
        Post a = op.get();
        postRepository.delete(a);
    }
}
