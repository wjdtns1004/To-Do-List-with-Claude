package com.example.service;

import com.example.model.Todo;
import java.util.List;

/**
 * Todo 서비스 인터페이스
 */
public interface TodoService {
    /** 전체 할일 목록 조회 */
    List<Todo> getAll();

    /** 할일 등록 */
    Todo create(String title);

    /** 할일 수정 (제목, 완료 여부) */
    Todo update(Long id, String title, boolean completed);

    /** 할일 삭제 */
    void delete(Long id);
}
