package com.example.feedservice.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "author_id", length = 64, nullable = false)
    private String authorId;

    @Column(name = "author_name", length = 128, nullable = false)
    private String authorName;

    @Column(name = "author_role", length = 64)
    private String authorRole;

    @Column(length = 255)
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String body;

    /**
     * Use TIMESTAMP with a DB default. Note: columnDefinition contains SQL used when DDL is generated.
     * If you prefer DB-managed timestamps, keep columnDefinition and remove setting in lifecycle callbacks.
     */
    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP")
    private Instant updatedAt;

    @Column(name = "upvote_count", nullable = false)
    private int upvoteCount = 0;

    @Column(name = "downvote_count", nullable = false)
    private int downvoteCount = 0;

    @Lob
    @Column(name = "attachments", columnDefinition = "LONGTEXT")
    private String attachments;

    public Post() {
        this.id = UUID.randomUUID().toString();
    }

    // JPA lifecycle hooks to ensure timestamps are set when persisting/updating.
    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (this.createdAt == null) this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // getters & setters
    public String getId() { return id; }

    public String getAuthorId() { return authorId; }
    public void setAuthorId(String authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public String getAuthorRole() { return authorRole; }
    public void setAuthorRole(String authorRole) { this.authorRole = authorRole; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }

    public int getUpvoteCount() { return upvoteCount; }
    public void setUpvoteCount(int upvoteCount) { this.upvoteCount = upvoteCount; }

    public int getDownvoteCount() { return downvoteCount; }
    public void setDownvoteCount(int downvoteCount) { this.downvoteCount = downvoteCount; }

    public String getAttachments() { return attachments; }
    public void setAttachments(String attachments) { this.attachments = attachments; }
}
