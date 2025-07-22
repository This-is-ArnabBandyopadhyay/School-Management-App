// File: SectionModel.java
package com.example.stuadminlogin.models;

public class SectionModel {
    private int id;
    private String name;
    private int classId;

    public SectionModel(int id, String name, int classId) {
        this.id = id;
        this.name = name;
        this.classId = classId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getClassId() { return classId; }

    public void setName(String name) { this.name = name; }
}
