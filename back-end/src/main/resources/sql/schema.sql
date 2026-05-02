-- 데이터베이스 생성 (필요시 사용)
-- CREATE DATABASE your_database CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 예시 테이블 생성
CREATE TABLE IF NOT EXISTS example (
    id          INT          NOT NULL AUTO_INCREMENT  COMMENT '고유 ID',
    name        VARCHAR(100) NOT NULL                COMMENT '이름',
    description VARCHAR(500)                         COMMENT '설명',
    created_at  DATETIME     NOT NULL DEFAULT NOW()  COMMENT '등록일시',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='예시 테이블';

-- 샘플 데이터
INSERT INTO example (name, description) VALUES
    ('샘플1', '첫 번째 샘플 데이터'),
    ('샘플2', '두 번째 샘플 데이터');
