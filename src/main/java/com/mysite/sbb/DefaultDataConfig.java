package com.mysite.sbb;

import com.mysite.sbb.Entity.Post;
import com.mysite.sbb.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultDataConfig {

    private final PostRepository postRepository;

    @Bean
    public ApplicationRunner loadData() {
        return args -> {
            for (int i = 1; i <= 10; i++) {
                Post post = Post.builder()
                        .title("제목 " + i)
                        .content("내용 " + i)
                        .author("defaultUser")
                        .build();
                postRepository.save(post);
            }
            System.out.println("디폴트 게시물 10개 생성 완료");
        };
    }
}
