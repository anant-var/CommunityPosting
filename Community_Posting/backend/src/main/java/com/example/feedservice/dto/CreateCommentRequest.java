package com.example.feedservice.dto;

import java.util.List;
import java.util.Map;

public class CreateCommentRequest {
    public String content;
    public String parentCommentId;
    public List<Map<String,Object>> attachments;
}
