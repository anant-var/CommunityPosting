package com.example.feedservice.controller;

import com.example.feedservice.dto.CreatePostRequest;
import com.example.feedservice.dto.PostDto;
import com.example.feedservice.model.Post;
import com.example.feedservice.repo.PostRepository;
import com.example.feedservice.service.UserAdapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserAdapterService userAdapter;

    private final ObjectMapper mapper = new ObjectMapper();

    @GetMapping
    public ResponseEntity<?> listPosts(@RequestParam(defaultValue="0") int page,
                                       @RequestParam(defaultValue="10") int size) {
        var p = postRepo.findAll(PageRequest.of(page, size));
        List<PostDto> out = new ArrayList<>();
        for(Post post : p.getContent()){
            out.add(toDto(post));
        }
        Map<String,Object> resp = new HashMap<>();
        resp.put("content", out);
        resp.put("totalElements", p.getTotalElements());
        return ResponseEntity.ok(resp);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest req, HttpServletRequest httpReq) {
        var user = userAdapter.getUserFromRequest(httpReq);
        Post p = new Post();
        p.setAuthorId(user.id);
        p.setAuthorName(user.name);
        p.setAuthorRole(user.role);
        p.setTitle(req.title);
        p.setBody(req.body);
        p.setCreatedAt(Instant.now());
        p.setUpdatedAt(Instant.now());
        try {
            if(req.attachments != null){
                String json = mapper.writeValueAsString(req.attachments);
                p.setAttachments(json);
            }
        } catch(Exception ex){
            p.setAttachments(null);
        }
        Post saved = postRepo.save(p);
        return ResponseEntity.ok(toDto(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable String id){
        var opt = postRepo.findById(id);
        if(opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDto(opt.get()));
    }

    private PostDto toDto(Post p){
        PostDto d = new PostDto();
        d.id = p.getId();
        d.authorId = p.getAuthorId();
        d.author_name = p.getAuthorName();
        d.author_role = p.getAuthorRole();
        d.title = p.getTitle();
        d.body = p.getBody();
        d.created_at = p.getCreatedAt();
        d.updated_at = p.getUpdatedAt();
        d.upvote_count = p.getUpvoteCount();
        d.downvote_count = p.getDownvoteCount();
        try{
            if(p.getAttachments() != null){
                d.attachments = mapper.readValue(p.getAttachments(), List.class);
            }
        }catch(Exception ex){
            d.attachments = null;
        }
        return d;
    }
}
