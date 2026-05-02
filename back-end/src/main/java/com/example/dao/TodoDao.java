package com.example.dao;

import com.example.model.Todo;
import java.util.List;

public interface TodoDao {
    List<Todo> selectAll();
    void insert(Todo todo);
    void update(Todo todo);
    void delete(Long id);
}
