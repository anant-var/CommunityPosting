package com.example.feedservice.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class CommentDto {
    public String id;
    public String postId;
    public String parentCommentId;
    public String authorId;
    public String author_name;
    public String author_role;
    public String content;
    public Instant created_at;
    public List<Map<String,Object>> attachments;
}
