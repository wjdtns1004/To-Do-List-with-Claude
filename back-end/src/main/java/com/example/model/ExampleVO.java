package com.example.model;

/**
 * 예시 VO 클래스 - 실제 도메인에 맞게 수정하여 사용
 */
public class ExampleVO {

    private int id;
    private String name;
    private String description;
    private String createdAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ExampleVO{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }
}
