package com.example.controller;

import com.example.model.Todo;
import com.example.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Todo REST 컨트롤러
 * 기본 URL: /api/todos
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    /** 전체 할일 목록 조회 */
    @GetMapping
    public List<Todo> getAll() {
        return todoService.getAll();
    }

    /** 할일 등록 - 요청: { "title": "..." } */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody Map<String, String> body) {
        try {
            return todoService.create(body.get("title"));
        } catch (IllegalArgumentException e) {
            logger.warn("할일 등록 실패 - 잘못된 입력: {}", e.getMessage());
            throw e;
        }
    }

    /** 할일 수정 - 요청: { "title": "...", "completed": true/false } */
    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id,
                       @RequestBody Map<String, Object> body) {
        try {
            String title = (String) body.get("title");
            boolean completed = (Boolean) body.get("completed");
            return todoService.update(id, title, completed);
        } catch (RuntimeException e) {
            logger.warn("할일 수정 실패 - id: {}, 원인: {}", id, e.getMessage());
            throw e;
        }
    }

    /** 할일 삭제 */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            todoService.delete(id);
        } catch (RuntimeException e) {
            logger.warn("할일 삭제 실패 - id: {}, 원인: {}", id, e.getMessage());
            throw e;
        }
    }
}
