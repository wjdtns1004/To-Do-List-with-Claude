/**
 * API 통신 모듈
 * 백엔드 REST API 호출을 담당
 */

const API_BASE_URL = 'http://localhost:8080/api';

/**
 * 공통 fetch 래퍼 - 모든 API 호출에 try-catch 적용
 */
async function fetchApi(url, options = {}) {
    try {
        const defaultOptions = {
            headers: { 'Content-Type': 'application/json' },
        };
        const mergedOptions = { ...defaultOptions, ...options };
        const response = await fetch(url, mergedOptions);

        if (!response.ok) {
            throw new Error(`HTTP 오류: ${response.status}`);
        }
        // 204 No Content (삭제 응답)는 JSON 없음
        if (response.status === 204) return null;
        return await response.json();
    } catch (error) {
        console.error('API 호출 실패:', error);
        throw error;
    }
}

/** 전체 할일 목록 조회 */
async function getTodos() {
    try {
        return await fetchApi(`${API_BASE_URL}/todos`);
    } catch (error) {
        console.error('목록 조회 실패:', error);
        throw error;
    }
}

/** 할일 등록 */
async function createTodo(title) {
    try {
        return await fetchApi(`${API_BASE_URL}/todos`, {
            method: 'POST',
            body: JSON.stringify({ title }),
        });
    } catch (error) {
        console.error('등록 실패:', error);
        throw error;
    }
}

/** 할일 수정 (제목 변경 또는 완료 토글) */
async function updateTodo(id, title, completed) {
    try {
        return await fetchApi(`${API_BASE_URL}/todos/${id}`, {
            method: 'PUT',
            body: JSON.stringify({ title, completed }),
        });
    } catch (error) {
        console.error('수정 실패:', error);
        throw error;
    }
}

/** 할일 삭제 */
async function deleteTodo(id) {
    try {
        return await fetchApi(`${API_BASE_URL}/todos/${id}`, {
            method: 'DELETE',
        });
    } catch (error) {
        console.error('삭제 실패:', error);
        throw error;
    }
}
