package com.example.feedservice.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class PostDto {
    public String id;
    public String authorId;
    public String author_name;
    public String author_role;
    public String title;
    public String body;
    public Instant created_at;
    public Instant updated_at;
    public int upvote_count;
    public int downvote_count;
    public List<Map<String,Object>> attachments;
}
