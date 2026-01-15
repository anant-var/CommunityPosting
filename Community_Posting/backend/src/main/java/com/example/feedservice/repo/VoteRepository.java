package com.example.feedservice.repo;

import com.example.feedservice.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, String> {
    Optional<Vote> findByUserIdAndPostId(String userId, String postId);
    long countByPostIdAndVoteType(String postId, String voteType);
}
