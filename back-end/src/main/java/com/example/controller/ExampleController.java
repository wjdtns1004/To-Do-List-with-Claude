package com.example.controller;

import com.example.model.ExampleVO;
import com.example.service.ExampleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 예시 REST 컨트롤러
 * 기본 URL: /api/example
 */
@RestController
@RequestMapping("/example")
public class ExampleController {

    private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

    @Autowired
    private ExampleService exampleService;

    /** 전체 목록 조회 */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> result = new HashMap<>();
        try {
            List<ExampleVO> list = exampleService.getAll();
            result.put("success", true);
//            result.put("message", "성공했습니다.");
            result.put("data", list);
        } catch (Exception e) {
            logger.error("목록 조회 실패", e);
            result.put("success", false);
            result.put("message", "목록 조회 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok(result);
    }

    /** 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable int id) {
        Map<String, Object> result = new HashMap<>();
        try {
            ExampleVO vo = exampleService.getById(id);
            if (vo == null) {
                result.put("success", false);
                result.put("message", "데이터를 찾을 수 없습니다.");
                return ResponseEntity.notFound().build();
            }
            result.put("success", true);
            result.put("data", vo);
        } catch (Exception e) {
            logger.error("단건 조회 실패 - id: {}", id, e);
            result.put("success", false);
            result.put("message", "조회 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok(result);
    }

    /** 등록 */
    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody ExampleVO vo) {
        Map<String, Object> result = new HashMap<>();
        try {
            int cnt = exampleService.create(vo);
            result.put("success", cnt > 0);
            result.put("message", cnt > 0 ? "등록되었습니다." : "등록에 실패했습니다.");
        } catch (Exception e) {
            logger.error("등록 실패", e);
            result.put("success", false);
            result.put("message", "등록 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok(result);
    }

    /** 수정 */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable int id, @RequestBody ExampleVO vo) {
        Map<String, Object> result = new HashMap<>();
        try {
            vo.setId(id);
            int cnt = exampleService.modify(vo);
            result.put("success", cnt > 0);
            result.put("message", cnt > 0 ? "수정되었습니다." : "수정에 실패했습니다.");
        } catch (Exception e) {
            logger.error("수정 실패 - id: {}", id, e);
            result.put("success", false);
            result.put("message", "수정 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok(result);
    }

    /** 삭제 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        Map<String, Object> result = new HashMap<>();
        try {
            int cnt = exampleService.remove(id);
            result.put("success", cnt > 0);
            result.put("message", cnt > 0 ? "삭제되었습니다." : "삭제에 실패했습니다.");
        } catch (Exception e) {
            logger.error("삭제 실패 - id: {}", id, e);
            result.put("success", false);
            result.put("message", "삭제 중 오류가 발생했습니다.");
            return ResponseEntity.internalServerError().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
