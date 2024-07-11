package com.example.touristapplicationbyhighhopes;

public class CommentItem {
    private String userName;
    private String commentText;

    public CommentItem(String userName, String commentText) {
        this.userName = userName;
        this.commentText = commentText;
    }

    public String getUserName() {
        return userName;
    }

    public String getCommentText() {
        return commentText;
    }
}
