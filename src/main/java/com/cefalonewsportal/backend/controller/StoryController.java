package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {
    @Autowired
    StoryService storyService;

    /*A user creating a new story*/
    @PostMapping("/api/{userId}/stories")
    public ResponseEntity<Story> createStory(@PathVariable("userId") int userId , @RequestBody Story story ){
    User user = storyService.findByIdUser(userId);
    if(user == null){

        return ResponseEntity.notFound().build();

    }
    story.setUser(user);

    return ResponseEntity.ok().body(storyService.save(story));
    }

    /*Get all stories*/
    @GetMapping("/api/stories")
    public List<Story> getAllStories(){

        System.out.println(storyService.findAll());
        return storyService.findAll();
    }

    /*Get all stories of a user_id*/
    @GetMapping("/api/{userId}/stories")
    public ResponseEntity< List<Story> > getAllStoriesByUserId(@PathVariable("userId") int userId){
        User user = storyService.findByIdUser(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(storyService.findByUserId(userId));

    }

    /*Update a story of a user_id*/
    @PutMapping("/api/{userId}/stories/{storyId}")
    public ResponseEntity<Story> updateStoryByUserId(@PathVariable("userId") int userId, @PathVariable("storyId") int storyId,@RequestBody Story newStory){

        User user = storyService.findByIdUser(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        Story story = storyService.findById(storyId);
        if(story == null){
            return ResponseEntity.notFound().build();
        }

        if(story.getUser()!=user){
            return ResponseEntity.badRequest().build();
        }
        story = storyService.updateStory(story,newStory);
        storyService.save(story);
        return ResponseEntity.ok().body(story);

    }

    /*Delete a story of a user_id*/
    @DeleteMapping("/api/{userId}/stories/{storyId}")
    public ResponseEntity<Story> deleteStoryByUserId(@PathVariable("userId") int userId , @PathVariable("storyId") int storyId){

//        User user = storyService.findByIdUser(userId);
//        if(user == null){
//            return ResponseEntity.notFound().build();
//        }
//        Story story = storyService.findById(storyId);
//        if(story == null){
//            return ResponseEntity.notFound().build();
//        }
//        if(story.getUser()!=user){
//            return ResponseEntity.badRequest().build();
//        }
//        storyService.deleteStory(story);
//        return ResponseEntity.ok().body(story);

        Story story = storyService.findByIdAndUserId(storyId,userId);
        if(story == null){
            return ResponseEntity.notFound().build();
        }
        storyService.deleteStory(story);
        return ResponseEntity.ok().body(story);


    }



}
