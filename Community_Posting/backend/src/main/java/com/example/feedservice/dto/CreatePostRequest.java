package com.example.feedservice.dto;

import java.util.List;
import java.util.Map;

public class CreatePostRequest {
    public String title;
    public String body;
    public List<Map<String,Object>> attachments;
}
