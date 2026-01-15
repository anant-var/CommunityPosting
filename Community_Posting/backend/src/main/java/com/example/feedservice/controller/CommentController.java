package com.example.feedservice.controller;

import com.example.feedservice.dto.CommentDto;
import com.example.feedservice.dto.CreateCommentRequest;
import com.example.feedservice.model.Comment;
import com.example.feedservice.model.Post;
import com.example.feedservice.repo.CommentRepository;
import com.example.feedservice.repo.PostRepository;
import com.example.feedservice.service.UserAdapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserAdapterService userAdapter;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<?> getComments(@PathVariable String postId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "50") int size){
        List<Comment> list = commentRepo.findByPostIdAndParentCommentIdIsNullOrderByCreatedAtDesc(postId, PageRequest.of(page, size));
        List<CommentDto> out = new ArrayList<>();
        for(Comment c : list){
            CommentDto d = new CommentDto();
            d.id = c.getId();
            d.postId = c.getPostId();
            d.parentCommentId = c.getParentCommentId();
            d.authorId = c.getAuthorId();
            d.author_name = c.getAuthorName();
            d.author_role = c.getAuthorRole();
            d.content = c.getContent();
            d.created_at = c.getCreatedAt();
            out.add(d);
        }
        return ResponseEntity.ok(out);
    }

    @GetMapping("/comments/{commentId}/replies")
    public ResponseEntity<?> getReplies(@PathVariable String commentId){
        List<Comment> replies = commentRepo.findByParentCommentIdOrderByCreatedAtAsc(commentId);
        List<CommentDto> out = new ArrayList<>();
        for(Comment c : replies){
            CommentDto d = new CommentDto();
            d.id = c.getId();
            d.postId = c.getPostId();
            d.parentCommentId = c.getParentCommentId();
            d.authorId = c.getAuthorId();
            d.author_name = c.getAuthorName();
            d.author_role = c.getAuthorRole();
            d.content = c.getContent();
            d.created_at = c.getCreatedAt();
            out.add(d);
        }
        return ResponseEntity.ok(out);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<?> createComment(@PathVariable String postId, @RequestBody CreateCommentRequest req, HttpServletRequest httpReq){
        var user = userAdapter.getUserFromRequest(httpReq);
        Optional<Post> p = postRepo.findById(postId);
        if(p.isEmpty()) return ResponseEntity.notFound().build();

        Comment c = new Comment();
        c.setPostId(postId);
        c.setParentCommentId(req.parentCommentId);
        c.setAuthorId(user.id);
        c.setAuthorName(user.name);
        c.setAuthorRole(user.role);
        c.setContent(req.content);
        Comment saved = commentRepo.save(c);

        CommentDto dto = new CommentDto();
        dto.id = saved.getId();
        dto.postId = saved.getPostId();
        dto.parentCommentId = saved.getParentCommentId();
        dto.authorId = saved.getAuthorId();
        dto.author_name = saved.getAuthorName();
        dto.author_role = saved.getAuthorRole();
        dto.content = saved.getContent();
        dto.created_at = saved.getCreatedAt();
        return ResponseEntity.ok(dto);
    }
}
