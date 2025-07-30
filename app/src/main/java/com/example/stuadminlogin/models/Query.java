// package com.example.stuadminlogin.models;

// public class Query {
//     private int queryId;
//     private int studentId;
//     private String queryText;
//     private String responseStatus;
//     private String generatedAt;

//     // 🔹 Add these for responded queries
//     private String responseText;
//     private String respondedAt;

//     // Getters and setters
//     public int getQueryId() { return queryId; }
//     public void setQueryId(int queryId) { this.queryId = queryId; }

//     public int getStudentId() { return studentId; }
//     public void setStudentId(int studentId) { this.studentId = studentId; }

//     public String getQueryText() { return queryText; }
//     public void setQueryText(String queryText) { this.queryText = queryText; }

//     public String getResponseStatus() { return responseStatus; }
//     public void setResponseStatus(String responseStatus) { this.responseStatus = responseStatus; }

//     public String getGeneratedAt() { return generatedAt; }
//     public void setGeneratedAt(String generatedAt) { this.generatedAt = generatedAt; }

//     // 🔹 New Getters and Setters for response
//     public String getResponseText() { return responseText; }
//     public void setResponseText(String responseText) { this.responseText = responseText; }

//     public String getRespondedAt() { return respondedAt; }
//     public void setRespondedAt(String respondedAt) { this.respondedAt = respondedAt; }
// }


package com.example.stuadminlogin.models;

public class Query {
    private int queryId;
    private int studentId;
    private int parentId;
    private int linkedStudentId; // NEW: Added for parent queries about a specific child
    private String queryText;
    private String responseStatus;
    private String generatedAt;

    private String responseText;
    private String respondedAt;

    // Getters and setters
    public int getQueryId() { return queryId; }
    public void setQueryId(int queryId) { this.queryId = queryId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getParentId() { return parentId; }
    public void setParentId(int parentId) { this.parentId = parentId; }

    // New Getter and Setter for linkedStudentId
    public int getLinkedStudentId() { return linkedStudentId; }
    public void setLinkedStudentId(int linkedStudentId) { this.linkedStudentId = linkedStudentId; }

    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }

    public String getResponseStatus() { return responseStatus; }
    public void setResponseStatus(String responseStatus) { this.responseStatus = responseStatus; }

    public String getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(String generatedAt) { this.generatedAt = generatedAt; }

    public String getResponseText() { return responseText; }
    public void setResponseText(String responseText) { this.responseText = responseText; }

    public String getRespondedAt() { return respondedAt; }
    public void setRespondedAt(String respondedAt) { this.respondedAt = respondedAt; }
}