/**
 * UI 렌더링 및 이벤트 처리 모듈
 */

// 현재 탭 필터 상태 ('all' | 'incomplete' | 'complete')
let currentFilter = 'all';
// 전체 할일 목록 (로컬 캐시)
let todos = [];

/** 알림 메시지 표시 (3초 후 자동 숨김) */
function showAlert(message, isError = true) {
    const alertEl = document.getElementById('alert');
    alertEl.textContent = message;
    alertEl.className = `alert ${isError ? 'error' : 'success'}`;
    setTimeout(() => { alertEl.className = 'alert hidden'; }, 3000);
}

/** XSS 방지용 HTML 이스케이프 */
function escapeHtml(str) {
    return str
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

/** 현재 필터에 맞는 목록을 화면에 렌더링 */
function renderList() {
    const ul = document.getElementById('todoList');
    const filtered = todos.filter(todo => {
        if (currentFilter === 'incomplete') return !todo.completed;
        if (currentFilter === 'complete')   return todo.completed;
        return true;
    });

    ul.innerHTML = filtered.map(todo => `
        <li class="todo-item ${todo.completed ? 'completed' : ''}">
            <input type="checkbox" ${todo.completed ? 'checked' : ''}
                   onchange="handleToggle(${todo.id}, this.checked)">
            <span class="todo-title">${escapeHtml(todo.title)}</span>
            <button class="btn-delete" onclick="handleDelete(${todo.id})">삭제</button>
        </li>
    `).join('');

    const total = todos.length;
    const done  = todos.filter(t => t.completed).length;
    document.getElementById('count').textContent = `전체 ${total}개 · 완료 ${done}개`;
}

/** 서버에서 전체 목록 불러오기 */
async function loadTodos() {
    try {
        todos = await getTodos();
        renderList();
    } catch (error) {
        showAlert('목록을 불러오는데 실패했습니다.');
    }
}

/** 할일 추가 */
async function handleAdd() {
    const input = document.getElementById('todoInput');
    const title = input.value.trim();
    if (!title) {
        showAlert('할일을 입력해주세요.');
        return;
    }
    try {
        const newTodo = await createTodo(title);
        todos.unshift(newTodo);
        input.value = '';
        renderList();
    } catch (error) {
        showAlert('등록에 실패했습니다.');
    }
}

/** 완료 여부 토글 - title은 로컬 배열에서 조회 (HTML 속성 인젝션 방지) */
async function handleToggle(id, completed) {
    const todo = todos.find(t => t.id === id);
    if (!todo) return;
    try {
        const updated = await updateTodo(id, todo.title, completed);
        todos = todos.map(t => t.id === id ? { ...t, completed: updated.completed } : t);
        renderList();
    } catch (error) {
        showAlert('수정에 실패했습니다.');
        loadTodos(); // 실패 시 서버 상태로 복구
    }
}

/** 할일 삭제 */
async function handleDelete(id) {
    try {
        await deleteTodo(id);
        todos = todos.filter(t => t.id !== id);
        renderList();
    } catch (error) {
        showAlert('삭제에 실패했습니다.');
    }
}

/** 탭 클릭 - 필터 변경 */
function handleTabClick(filter) {
    currentFilter = filter;
    document.querySelectorAll('.tab').forEach(tab => {
        tab.classList.toggle('active', tab.dataset.filter === filter);
    });
    renderList();
}

/** 페이지 로드 시 초기화 */
document.addEventListener('DOMContentLoaded', () => {
    loadTodos();

    document.getElementById('btnAdd').addEventListener('click', handleAdd);
    document.getElementById('todoInput').addEventListener('keydown', e => {
        if (e.key === 'Enter') handleAdd();
    });
    document.querySelectorAll('.tab').forEach(tab => {
        tab.addEventListener('click', () => handleTabClick(tab.dataset.filter));
    });
});
