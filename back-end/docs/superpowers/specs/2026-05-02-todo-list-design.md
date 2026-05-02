# To-Do List 설계 문서

**날짜:** 2026-05-02  
**스택:** Spring MVC 5.3 + MyBatis + MySQL / Vanilla JS + HTML

---

## 1. 개요

인증 없이 누구나 사용할 수 있는 단순 CRUD to-do list 애플리케이션.  
프론트엔드(HTML/JS)와 백엔드(Spring REST API)를 분리하여 구현한다.

---

## 2. 기능 범위

- 할일 등록 (제목 입력)
- 할일 목록 조회 (전체 / 미완료 / 완료 탭 필터)
- 완료 여부 토글 (체크박스)
- 할일 삭제
- 인증 없음, 단일 사용자 기준

---

## 3. 아키텍처

```
[브라우저 front-end]
  index.html          ← 탭 UI, 입력폼, 목록 렌더링
  js/api.js           ← fetch 래퍼 + todo CRUD 함수
  js/main.js          ← 이벤트 핸들러, 화면 렌더링 로직
  css/style.css       ← 스타일

        ↕ HTTP JSON (REST, 포트 8080)

[back-end Spring MVC]
  TodoController      ← GET/POST/PUT/DELETE /api/todos
  TodoService         ← 비즈니스 로직
  TodoMapper          ← MyBatis 인터페이스
  TodoMapper.xml      ← SQL
  Todo.java           ← VO

        ↕ JDBC (HikariCP)

[MySQL]
  테이블: todo
```

---

## 4. 데이터베이스

```sql
CREATE TABLE todo (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    title      VARCHAR(200) NOT NULL,
    completed  TINYINT(1)   NOT NULL DEFAULT 0,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

---

## 5. REST API

| 메서드 | URL | 요청 바디 | 응답 | 설명 |
|--------|-----|-----------|------|------|
| GET | `/api/todos` | - | `List<Todo>` | 전체 목록 조회 |
| POST | `/api/todos` | `{ "title": "..." }` | `Todo` | 등록 |
| PUT | `/api/todos/{id}` | `{ "title": "...", "completed": true/false }` | `Todo` | 제목 수정 또는 완료 토글 |
| DELETE | `/api/todos/{id}` | - | - (204) | 삭제 |

---

## 6. 백엔드 파일 구성

```
back-end/src/main/java/com/example/
  controller/TodoController.java
  service/TodoService.java
  service/TodoServiceImpl.java
  mapper/TodoMapper.java
  vo/Todo.java

back-end/src/main/resources/
  mapper/TodoMapper.xml
  mybatis-config.xml
  logback.xml

back-end/src/main/webapp/WEB-INF/
  web.xml                     ← DispatcherServlet 등록
  spring/app-context.xml      ← DataSource, MyBatis, Transaction 설정
  spring/mvc-context.xml      ← Spring MVC 설정 (CORS 포함)
```

---

## 7. 프론트엔드 파일 구성

```
front-end/
  index.html          ← 기존 파일 교체
  css/style.css       ← 기존 파일 확장
  js/api.js           ← getTodos, createTodo, updateTodo, deleteTodo
  js/main.js          ← 탭 필터 상태, 렌더링, 이벤트 바인딩
```

---

## 8. 에러 처리

- 모든 API 호출은 try-catch로 감쌈 (기존 `fetchApi` 래퍼 패턴 유지)
- API 실패 시: 콘솔 에러 로깅 + 화면 상단 알림 메시지 표시
- 프론트 validation: 빈 제목 등록 방지

---

## 9. Todo VO 필드

| 필드 | 타입 | 설명 |
|------|------|------|
| id | Long | PK, 자동 증가 |
| title | String | 할일 제목 |
| completed | boolean | 완료 여부 (false = 미완료) |
| createdAt | LocalDateTime | 등록 시각 |
