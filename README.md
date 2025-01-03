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
