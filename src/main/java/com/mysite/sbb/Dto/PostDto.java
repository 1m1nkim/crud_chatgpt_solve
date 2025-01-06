package com.mysite.sbb.Dto;

import com.mysite.sbb.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
//빌더 어노테이션을 사용하면 기본 생성자가 없다
// 그래서 Json객체를 생성할 수 없어서
//@NoArgsConstructor를 사용하는데 모든 필드 포함 생성자도 없기 때ㅔ문에
//AllArgsConstructor도 같이 사용해준다
public class PostDto {
    private Long id;
    private String title;
    private String content;

    public static PostDto of(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }


    public static List<PostDto> fromEntity(List<Post> post){
        return post.stream()
                .map(PostDto::of)
                .toList();
    }
}
