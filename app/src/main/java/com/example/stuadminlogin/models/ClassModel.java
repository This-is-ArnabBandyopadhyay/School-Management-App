package com.example.stuadminlogin.models;

public class ClassModel {
    private int id;
    private String name;
    private String code;

    public ClassModel(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCode() { return code; }

    public void setName(String name) { this.name = name; }
    public void setCode(String code) { this.code = code; }
}
