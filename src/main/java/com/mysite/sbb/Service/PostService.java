package com.mysite.sbb.Service;

import com.mysite.sbb.Entity.Post;
import com.mysite.sbb.Repository.PostRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getList(){
        return postRepository.findAll();
    }

    //@PathVariable은 경로변수를 자동으로 매핑해준다
    //즉 http://localhost:8080/api/post/id=1 이런식으로 쿼리 파라미터 형식으로 매핑해주는 것이 아닌
    //http://localhost:8080/api/post/1 이런식으로 경로 변수를 매핑해줘야한다.
    public String findIdContent(@PathVariable("id") Long id){
        //Optional을 사용하는 것 보단 orElseThrow로 예외 처리하는 것이 더 안전하고 간결해진다
        //Optional<Post> op = postRepository.findById(id);
        //Post a = op.get();

        Post a  = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
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
    public Post create(@RequestBody @Valid Post post) {
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

    //PathVariable은 service에서 처리하는 것이 아닌 controller에서만 처리하기에 제거한다.
    public Post update(Long id, @RequestBody @Valid Post updatedPost) {
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        post.setTitle(updatedPost.getTitle());
        post.setContent(updatedPost.getContent());
        return postRepository.save(post);
    }


    public void delete(Long id){
        Optional<Post> op = postRepository.findById(id);
        Post a = op.get();
        postRepository.delete(a);
    }

    public List<Post> searchTitle(String title){
        //return postRepository.findByTitleContaining(title);
        //이렇게 하면 빈 리스트도 반환 될 수 있기 때문에

        List<Post> posts = postRepository.findByTitleContaining(title);
        if(posts.isEmpty()){
            throw new IllegalArgumentException("검색 결과가 없습니다.");
        }
        return posts;
    }

    public Page<Post> searchTitleWithPaging(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword, pageable);
    }

    public Page<Post> getAllPosts(int page) {
        int pageSize = 3; // 고정된 페이지 크기
        PageRequest pageable = PageRequest.of(page, pageSize);
        return postRepository.findAll(pageable);
    }
    //페이지 처리는 고정된 크기로 할거면 페이지사이즈를 정해주고
    // PageRequest에 값을 파라미터 페이지 값, pagesize 값을 정해주면 해당 페이지로 이동할 수 있게 해줌 
}
