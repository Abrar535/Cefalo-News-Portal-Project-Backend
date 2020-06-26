package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findByCommentIdAndStoryStoryId(Integer commentId, Integer storyId);
    List<Comment> findByStoryStoryId(Integer storyId);
}
