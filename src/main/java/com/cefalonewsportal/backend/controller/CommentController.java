package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.Comment;
import com.cefalonewsportal.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

@PostMapping("/api/public/stories/{storyId}/comments")
    public ResponseEntity<?> createComment(@RequestBody Comment comment,@PathVariable("storyId") Integer storyId){
    comment = commentService.createComment(storyId,comment);
    if(comment == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such story found to comment!");
    }
    return ResponseEntity.ok().body(comment);


}


}
