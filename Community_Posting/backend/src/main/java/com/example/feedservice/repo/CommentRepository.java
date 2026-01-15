package com.example.feedservice.repo;

import com.example.feedservice.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByPostIdAndParentCommentIdIsNullOrderByCreatedAtDesc(String postId, Pageable pageable);
    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(String parentCommentId);
}
