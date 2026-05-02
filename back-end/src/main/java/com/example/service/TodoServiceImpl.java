package com.example.service;

import com.example.dao.TodoDao;
import com.example.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Todo 서비스 구현체
 */
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;

    /** 전체 할일 목록 조회 */
    @Override
    @Transactional(readOnly = true)
    public List<Todo> getAll() {
        return todoDao.selectAll();
    }

    /** 할일 등록 - 빈 제목 불가, 새 Todo 생성 후 DB 저장 */
    @Override
    @Transactional
    public Todo create(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("할일 제목은 비워둘 수 없습니다.");
        }
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        todoDao.insert(todo);
        return todo;
    }

    /** 할일 수정 - 존재하지 않는 id이면 예외 발생 */
    @Override
    @Transactional
    public Todo update(Long id, String title, boolean completed) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("할일 제목은 비워둘 수 없습니다.");
        }
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(title);
        todo.setCompleted(completed);
        int affected = todoDao.update(todo);
        if (affected == 0) {
            throw new RuntimeException("해당 할일을 찾을 수 없습니다. id=" + id);
        }
        return todo;
    }

    /** 할일 삭제 - 존재하지 않는 id이면 예외 발생 */
    @Override
    @Transactional
    public void delete(Long id) {
        int affected = todoDao.delete(id);
        if (affected == 0) {
            throw new RuntimeException("해당 할일을 찾을 수 없습니다. id=" + id);
        }
    }
}
