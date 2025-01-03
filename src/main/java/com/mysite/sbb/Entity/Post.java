package com.mysite.sbb.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
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
}
