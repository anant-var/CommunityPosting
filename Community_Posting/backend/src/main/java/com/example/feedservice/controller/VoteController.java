package com.example.feedservice.controller;

import com.example.feedservice.dto.VoteRequest;
import com.example.feedservice.model.Post;
import com.example.feedservice.model.Vote;
import com.example.feedservice.repo.PostRepository;
import com.example.feedservice.repo.VoteRepository;
import com.example.feedservice.service.UserAdapterService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class VoteController {

    @Autowired
    private VoteRepository voteRepo;

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserAdapterService userAdapter;

    @GetMapping("/{postId}/vote-status")
    public ResponseEntity<?> status(@PathVariable String postId, HttpServletRequest req){
        var user = userAdapter.getUserFromRequest(req);
        Optional<Vote> ov = voteRepo.findByUserIdAndPostId(user.id, postId);
        String userVote = ov.map(Vote::getVoteType).orElse(null);
        return ResponseEntity.ok(Map.of("userVote", userVote));
    }

    @PostMapping("/{postId}/vote")
    @Transactional
    public ResponseEntity<?> vote(@PathVariable String postId, @RequestBody VoteRequest body, HttpServletRequest req){
        var user = userAdapter.getUserFromRequest(req);
        if(!("UP".equals(body.vote) || "DOWN".equals(body.vote))){
            return ResponseEntity.badRequest().body(Map.of("error","vote must be 'UP' or 'DOWN'"));
        }

        Post post = postRepo.findById(postId).orElse(null);
        if(post == null) return ResponseEntity.notFound().build();

        Optional<Vote> opt = voteRepo.findByUserIdAndPostId(user.id, postId);

        if(opt.isPresent()){
            Vote existing = opt.get();
            if(existing.getVoteType().equals(body.vote)){
                // idempotent no-op
            } else {
                existing.setVoteType(body.vote);
                voteRepo.save(existing);
                if(body.vote.equals("UP")){
                    post.setUpvoteCount(post.getUpvoteCount() + 1);
                    post.setDownvoteCount(Math.max(0, post.getDownvoteCount() - 1));
                } else {
                    post.setDownvoteCount(post.getDownvoteCount() + 1);
                    post.setUpvoteCount(Math.max(0, post.getUpvoteCount() - 1));
                }
                postRepo.save(post);
            }
        } else {
            Vote v = new Vote();
            v.setPostId(postId);
            v.setUserId(user.id);
            v.setVoteType(body.vote);
            voteRepo.save(v);
            if(body.vote.equals("UP")){
                post.setUpvoteCount(post.getUpvoteCount() + 1);
            } else {
                post.setDownvoteCount(post.getDownvoteCount() + 1);
            }
            postRepo.save(post);
        }

        return ResponseEntity.ok(Map.of(
            "postId", post.getId(),
            "upvoteCount", post.getUpvoteCount(),
            "downvoteCount", post.getDownvoteCount(),
            "userVote", body.vote
        ));
    }
}
