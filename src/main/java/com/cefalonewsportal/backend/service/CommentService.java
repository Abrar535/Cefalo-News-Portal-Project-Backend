package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Comment;
import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    StoryService storyService;
    @Autowired
    CommentRepository commentRepository;

    public Comment findById(Integer commentId){

        return commentRepository.findById(commentId).orElse(null);

    }
    public Comment save(Comment comment){
        return commentRepository.save(comment);
    }

    public void delete(Comment comment){

        commentRepository.delete(comment);

    }

    public List<Comment> getAllCommentsByStoryId(Integer storyId){

    return commentRepository.findByStoryStoryId(storyId);

    }

    public Comment createComment(Integer storyId, Comment comment){
        Story story = storyService.findById(storyId);
        if(story == null){
            return null;
        }
        comment.setStory(story);
        comment = save(comment);
        return comment;
    }




    public Comment deleteComment(Integer commentId, Integer storyId){
        Comment comment = commentRepository.findByCommentIdAndStoryStoryId(commentId , storyId);
        if(comment == null ){
            return null ;
        }
        delete(comment);
        return comment;

    }


}
