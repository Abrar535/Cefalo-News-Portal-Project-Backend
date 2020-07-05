package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Comment;
import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = {"comments"})
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
    @Cacheable(key = "#storyId")
    public List<Comment> getAllCommentsByStoryId(Integer storyId){
    log.info("Get all comments of a storyId called");
    return commentRepository.findByStoryStoryId(storyId);

    }
    @CacheEvict(allEntries = true)
    public Comment createComment(Integer storyId, Comment comment){
        Story story = storyService.findById(storyId);
        if(story == null){
            return null;
        }
        comment.setStory(story);
        comment = save(comment);
        log.info("Create comment called");
        return comment;
    }



    @CacheEvict(allEntries = true)
    public Comment deleteComment(Integer commentId, Integer storyId){
        Comment comment = commentRepository.findByCommentIdAndStoryStoryId(commentId , storyId);
        if(comment == null ){
            return null ;
        }
        delete(comment);

        log.info("Delete comment called");
        return comment;

    }


}
