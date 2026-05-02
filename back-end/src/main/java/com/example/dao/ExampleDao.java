package com.example.dao;

import com.example.model.ExampleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 예시 DAO 인터페이스 - MyBatis Mapper 자동 스캔 대상
 */
@Mapper
public interface ExampleDao {

    /** 전체 목록 조회 */
    List<ExampleVO> selectAll();

    /** ID로 단건 조회 */
    ExampleVO selectById(int id);

    /** 등록 */
    int insert(ExampleVO vo);

    /** 수정 */
    int update(ExampleVO vo);

    /** 삭제 */
    int delete(int id);
}
