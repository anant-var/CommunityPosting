package com.example.feedservice.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "post_votes",
        uniqueConstraints = {
                @UniqueConstraint(name = "uniq_user_post", columnNames = {"user_id", "post_id"})
        }
)
public class Vote {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(name = "post_id", length = 36, nullable = false)
    private String postId;

    @Column(name = "user_id", length = 64, nullable = false)
    private String userId;

    @Column(name = "vote_type", length = 8, nullable = false)
    private String voteType;  // "UP" or "DOWN"

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Instant createdAt;

    public Vote() {
        this.id = UUID.randomUUID().toString();
    }

    // Ensure timestamp is set before INSERT
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = Instant.now();
        }
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVoteType() {
        return voteType;
    }

    public void setVoteType(String voteType) {
        this.voteType = voteType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
