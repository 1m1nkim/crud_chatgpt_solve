- 문제 예제는 ChatGpt 사용하여 진행하였습니다.
- 간단한 CRUD Test를 위해 프론트작업은 진행하지 않았습니다.

>#### 문제 시나리오
```
우리는 제목(title)과 내용(content)을 가지는 게시판(Post) 서비스를 만들려고 합니다. 
이를 위해 Spring Boot, Spring Data JPA, H2 DB 등을 사용할 것이고, 
Postman으로 CRUD 작업을 확인할 예정입니다.
```

>#### 전제 조건
```
Spring Boot를 사용한다고 가정(버전은 3.x 이상).
Entity를 이용해 DB 테이블과 매핑, Repository를 통해 DB 접근.
Controller를 통해 CRUD API 제공 (URI 예시: /api/posts).
DB는 간단히 H2 메모리 DB를 사용.
Lombok 사용 여부는 자유. (Lombok 사용 시 Getter/Setter/생성자 자동 생성 가능)
```

---

>##### 문제 목록

```
문제 1. Post 엔티티 설계하기
  Post라는 이름의 엔티티(클래스)를 만든다.
  다음과 같은 필드를 반드시 포함한다.
  Long id (기본 키로 사용, 자동 증가)
  String title (글 제목)
  String content (글 내용)
  title과 content에 대한 컬럼 설정을 어떻게 할지 결정해보자.
  예: title은 200자 이하로 제한, content는 제한 없이 TEXT로.
  (선택) Lombok을 사용할 경우, 아래 중 원하는 것을 선택해서 적용한다.
  @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor
  직접 getter, setter, 생성자 작성해도 OK.

  출제 의도:
  Spring Data JPA에서 엔티티를 어떻게 정의하고, 컬럼을 어떻게 설정하는지 연습해본다.
  Lombok 사용 시 필수 어노테이션을 이해한다.
```
```
문제 2. PostRepository 만들기
  org.springframework.data.jpa.repository.JpaRepository를 상속받는 PostRepository 인터페이스를 만든다.
  제네릭으로 <Post, Long>을 지정한다.
  커스텀 쿼리가 필요하다면(예: title로 검색), 메서드를 추가로 정의해본다.
  예: List<Post> findByTitleContaining(String keyword);
  
  출제 의도:
  JpaRepository를 확장해 기본 CRUD 기능을 편리하게 사용하는 방법을 익힌다.
  쿼리 메서드 작성 방식을 이해한다.
```
```
문제 3. PostController로 CRUD API 작성
  @RestController와 @RequestMapping("/api/posts")를 달아서 REST API 컨트롤러를 만든다.
  게시물 목록을 조회하는 GET 메서드를 만들어라.
  예: @GetMapping → 전체 Post 목록 반환.
  단일 게시물을 조회하는 GET 메서드를 만들어라.
  예: @GetMapping("/{id}") → 경로 변수로 id를 받아 해당 게시물 하나를 반환.
  게시물 생성을 위한 POST 메서드를 만들어라.
  예: @PostMapping → @RequestBody로 title, content를 받아 DB에 저장 후, 저장된 엔티티를 반환.
  게시물 수정을 위한 PUT 메서드를 만들어라.
  예: @PutMapping("/{id}") → 수정할 id와 수정할 내용(title, content)을 받아서 DB 갱신 후 반환.
  게시물 삭제를 위한 DELETE 메서드를 만들어라.
  예: @DeleteMapping("/{id}") → 해당 게시물을 삭제하고, 적절한 상태 코드를 반환.
  
  출제 의도:
  Spring Boot에서 RESTful API를 만드는 핵심 구조(Controller → Service/Repository) 이해.
  @RequestBody, @PathVariable 등 기본적인 어노테이션 사용법 숙지.
```
```
문제 4. Postman으로 CRUD 테스트하기
  GET http://localhost:8080/api/posts
  요청 시 전체 게시물 목록을 JSON으로 응답하는지 확인.
  GET http://localhost:8080/api/posts/{id}
  특정 id의 게시물을 불러올 수 있는지 테스트.
  POST http://localhost:8080/api/posts
  Body(JSON) 예시:
  {
    "title": "첫 번째 글",
    "content": "게시물 내용입니다."
  }
  정상적으로 DB에 저장되는지, 저장된 후 응답이 어떻게 오는지 확인.
  PUT http://localhost:8080/api/posts/{id}
  Body(JSON) 예시:
  {
    "title": "수정된 제목",
    "content": "수정된 내용"
  }
  데이터가 실제로 수정되는지, 수정 후 응답이 올바르게 오는지 확인.
  DELETE http://localhost:8080/api/posts/{id}
  요청 시 해당 id 게시물이 삭제되는지 확인. 이후 GET으로 조회했을 때 결과가 없어야 함.

  출제 의도:
  Postman을 통해 실제로 HTTP 요청을 보내보고, 
  Controller → Service → Repository → DB 순으로 흐름을 파악한다.
```
```
문제 5. DTO 변환 및 응답 객체 설계
엔티티 대신 DTO(Data Transfer Object)를 활용하여 클라이언트와 데이터를 주고받도록 구조를 변경하라.

요구사항:
1. PostResponseDTO와 PostRequestDTO를 설계하여, 엔티티 대신 클라이언트와 통신에 사용한다.
2. Controller에서 PostRequestDTO를 받아 서비스 계층으로 전달한다.
3. Service 계층에서 PostResponseDTO를 생성하여 반환한다.
4. 필요에 따라 MapStruct 또는 ModelMapper를 사용해 DTO 변환을 자동화한다.

출제 의도:
- 엔티티를 직접 노출하지 않고, DTO를 통해 데이터 구조를 통제하는 방법을 익힌다.
- 서비스 계층에서 데이터 변환 로직을 설계하는 방법을 이해한다.
```
```
문제 6. Spring Security와 Principal 적용
Spring Security를 사용하여 사용자를 인증하고, 인증된 사용자의 정보를 Principal로 관리하라.

요구사항:
1. Spring Security를 설정하여 기본 로그인 및 인증 기능을 활성화한다.
2. Principal을 활용하여 현재 로그인한 사용자의 정보를 컨트롤러에서 조회한다.
3. 게시글 작성 시 작성자 정보(작성자 id 또는 username)를 자동으로 추가한다.

출제 의도:
- Spring Security와 Principal 객체의 개념을 이해한다.
- 사용자 인증 정보와 게시판 기능을 연동하는 방법을 익힌다.
```
```

문제 7. 댓글 기능 구현
게시글에 대한 댓글 기능을 추가하라.

요구사항:
1. 댓글(Comment) 엔티티를 설계하고, 게시글(Post)와의 관계를 설정한다.
   - OneToMany 관계 설정.
   - 댓글 엔티티는 content, author, createdDate 필드를 포함한다.
2. /api/posts/{postId}/comments 경로를 통해 댓글의 CRUD를 지원한다.
3. 댓글 데이터는 PostResponseDTO에서 함께 반환되도록 설계한다.

출제 의도:
- JPA에서 OneToMany 관계를 설정하고 활용하는 방법을 이해한다.
- 댓글 기능을 게시판 서비스에 통합하는 방법을 익힌다.
```
```
문제 8. JWT 인증 구현
JWT(JSON Web Token)를 사용하여 사용자 인증 방식을 개선하라.

요구사항:
1. 사용자가 로그인하면 JWT를 발급하여 클라이언트에 반환한다.
2. 클라이언트는 JWT를 Authorization 헤더에 포함하여 요청을 보낸다.
3. Spring Security의 필터 체인에서 JWT를 검증하여 사용자 인증을 처리한다.
4. 기존 Spring Security의 Principal 인증 방식을 JWT 기반으로 리팩터링한다.

출제 의도:
- JWT의 기본 개념과 인증 구조를 이해한다.
- Spring Security와 JWT를 연동하여 인증 과정을 리팩터링한다.
```
```
문제 9. JWT의 AccessToken과 RefreshToken 구현
JWT 인증 방식을 개선하여 AccessToken과 RefreshToken을 활용한 인증 및 토큰 갱신 방식을 구현하라.

요구사항:
1. 로그인 시 AccessToken과 RefreshToken을 모두 발급한다.
   - AccessToken: 유효 기간이 짧음(30분 등).
   - RefreshToken: 유효 기간이 김(7일 등).
2. 클라이언트는 AccessToken 만료 시 RefreshToken을 통해 새로운 AccessToken을 요청한다.
3. RefreshToken은 HttpOnly 쿠키로 저장하며, 서버에서 관리한다.
4. Spring Security에서 AccessToken과 RefreshToken을 활용한 인증 및 갱신 로직을 구현한다.

출제 의도:
- JWT를 활용한 인증과 토큰 갱신 구조를 이해한다.
- RefreshToken의 보안성과 활용성을 익힌다.
```
```
문제 10. React 프론트엔드와 연동
React와의 연동을 고려하여 사용자 친화적인 UI/UX를 설계하고, API 통신을 원활히 지원하라.

요구사항:
1. React를 활용하여 다음 기능을 구현한다:
   - 로그인 및 로그아웃 페이지(Login.tsx).
   - 게시글 목록 조회 페이지(PostList.tsx) (페이징 포함).
   - 게시글 작성 페이지(CreatePost.tsx).
   - 게시글 상세 페이지(PostDetail.tsx) (댓글 포함).
2. React Router를 사용하여 페이지 간 이동을 처리한다.
3. Axios를 활용하여 React에서 Spring Boot API와 통신한다.
4. JWT 인증을 적용하여 로그인 상태를 관리한다.

출제 의도:
- React와 Spring Boot 간의 통신 구조를 설계하고 구현한다.
- React를 활용하여 사용자 친화적인 UI를 개발한다.
```
```
문제 11. 예외 처리 및 오류 메시지
백엔드와 프론트엔드의 예외 상황에 대해 적절한 처리를 구현하라.

요구사항:
1. 백엔드에서 발생할 수 있는 주요 예외를 처리하고, 적절한 상태 코드와 메시지를 반환한다.
   - 예: 게시글 조회 실패 시 "존재하지 않는 게시글입니다."
   - JWT 인증 실패 시 "토큰이 유효하지 않습니다."
2. React에서 예외 상황에 대해 사용자 친화적인 메시지를 표시한다.
3. Axios 인터셉터를 사용하여 HTTP 에러를 통합적으로 처리한다.

출제 의도:
- 백엔드와 프론트엔드에서 예외 처리를 설계하고 구현하는 방법을 이해한다.
- 사용자 경험(UX)을 고려한 오류 메시지를 설계한다.
```

최종 목표
이 모든 문제를 해결함으로써 React와 Spring Boot를 완벽히 연동한 CRUD 기반의 게시판 서비스를 구현할 수 있습니다.
또한, 보안 강화(JWT 인증)와 기능 확장(댓글, 페이징 등)을 통해 실제 서비스 수준의 프로젝트를 구축하는 방
