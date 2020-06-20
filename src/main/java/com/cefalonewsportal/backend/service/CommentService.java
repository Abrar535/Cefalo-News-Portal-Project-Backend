package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Comment;
import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    StoryService storyService;
    @Autowired
    CommentRepository commentRepository;
    public Comment createComment(Integer storyId, Comment comment){
        Story story = storyService.findById(storyId);
        if(story == null){
            return null;
        }
        comment.setStory(story);
        comment = save(comment);
        return comment;
    }

    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }



}
