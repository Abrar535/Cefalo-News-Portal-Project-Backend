package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.Comment;
import com.cefalonewsportal.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

@GetMapping("/api/public/stories/{storyId}/comments")
    public ResponseEntity<?> getCommentsByStoryId(@PathVariable("storyId") Integer storyId){

    return ResponseEntity.ok().body(commentService.getAllCommentsByStoryId(storyId));


}

@PostMapping("/api/public/stories/{storyId}/comments")
    public ResponseEntity<?> createComment(@RequestBody Comment comment,@PathVariable("storyId") Integer storyId){
    comment = commentService.createComment(storyId,comment);
    if(comment == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such story found to comment!");
    }
    return ResponseEntity.ok().body(comment);


}

@DeleteMapping("/api/public/stories/{storyId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("storyId") Integer storyId , @PathVariable("commentId") Integer commentId){

    Comment comment = commentService.deleteComment(commentId, storyId);
    if(comment == null){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment not found!");
    }
    return ResponseEntity.ok().body(comment);

}


}
