package com.example.dao;

import com.example.model.Todo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * Todo MyBatis Mapper 인터페이스
 */
@Mapper
public interface TodoDao {
    /** 전체 할일 목록 조회 */
    List<Todo> selectAll();

    /** 할일 등록 */
    int insert(Todo todo);

    /** 할일 수정 (제목, 완료 여부) */
    int update(Todo todo);

    /** 할일 삭제 */
    int delete(Long id);
}
