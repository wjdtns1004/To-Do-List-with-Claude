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
@Transactional
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;

    /** 전체 할일 목록 조회 */
    @Override
    public List<Todo> getAll() {
        return todoDao.selectAll();
    }

    /** 할일 등록 - title로 새 Todo 생성 후 DB에 저장 */
    @Override
    public Todo create(String title) {
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setCompleted(false);
        todoDao.insert(todo);
        return todo;
    }

    /** 할일 수정 - id로 조회 후 title, completed 업데이트 */
    @Override
    public Todo update(Long id, String title, boolean completed) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(title);
        todo.setCompleted(completed);
        todoDao.update(todo);
        return todo;
    }

    /** 할일 삭제 */
    @Override
    public void delete(Long id) {
        todoDao.delete(id);
    }
}
