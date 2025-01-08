package com.mysite.sbb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
//빌더 패턴을 추가하면 생성자가 자동으로 생성되지않아서  ~~Constructor를 추가해줘야함
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    @Column(length = 200)
    private String title;

    @NotBlank(message = "게시물 내용은 필수입니다.")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
