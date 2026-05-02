package com.example.service;

import com.example.dao.ExampleDao;
import com.example.model.ExampleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 예시 서비스 클래스
 */
@Service
public class ExampleService {

    @Autowired
    private ExampleDao exampleDao;

    /** 전체 목록 조회 */
    public List<ExampleVO> getAll() {
        return exampleDao.selectAll();
    }

    /** ID로 단건 조회 */
    public ExampleVO getById(int id) {
        return exampleDao.selectById(id);
    }

    /** 등록 */
    @Transactional
    public int create(ExampleVO vo) {
        return exampleDao.insert(vo);
    }

    /** 수정 */
    @Transactional
    public int modify(ExampleVO vo) {
        return exampleDao.update(vo);
    }

    /** 삭제 */
    @Transactional
    public int remove(int id) {
        return exampleDao.delete(id);
    }
}
