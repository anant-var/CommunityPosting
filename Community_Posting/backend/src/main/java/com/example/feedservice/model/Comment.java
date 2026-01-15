package com.example.feedservice.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "comments", indexes = {
        @Index(name="idx_post", columnList = "post_id")
})
public class Comment {
    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "post_id", length = 36, nullable = false)
    private String postId;

    @Column(name = "parent_comment_id", length = 36)
    private String parentCommentId;

    @Column(name="author_id", length=64, nullable=false)
    private String authorId;

    @Column(name="author_name", nullable=false)
    private String authorName;

    @Column(name="author_role")
    private String authorRole;

    // Use MySQL LONGTEXT for large text — avoids generation of CLOB
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name="created_at", nullable=false)
    private Instant createdAt;

    public Comment(){
        this.id = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
    }

    // getters & setters
    public String getId(){ return id; }
    public String getPostId(){ return postId; }
    public void setPostId(String postId){ this.postId = postId; }
    public String getParentCommentId(){ return parentCommentId; }
    public void setParentCommentId(String parentCommentId){ this.parentCommentId = parentCommentId; }
    public String getAuthorId(){ return authorId; }
    public void setAuthorId(String authorId){ this.authorId = authorId; }
    public String getAuthorName(){ return authorName; }
    public void setAuthorName(String authorName){ this.authorName = authorName; }
    public String getAuthorRole(){ return authorRole; }
    public void setAuthorRole(String authorRole){ this.authorRole = authorRole; }
    public String getContent(){ return content; }
    public void setContent(String content){ this.content = content; }
    public Instant getCreatedAt(){ return createdAt; }
}
