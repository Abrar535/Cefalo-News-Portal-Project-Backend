package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
