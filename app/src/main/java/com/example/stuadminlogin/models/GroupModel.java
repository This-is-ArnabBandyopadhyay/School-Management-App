package com.example.stuadminlogin.models;

import java.util.List;

public class GroupModel {
    private int groupId;
    private String groupName;
    private List<StudentModel> members;

    public GroupModel() {}

    public GroupModel(int groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<StudentModel> getMembers() {
        return members;
    }

    public void setMembers(List<StudentModel> members) {
        this.members = members;
    }
}