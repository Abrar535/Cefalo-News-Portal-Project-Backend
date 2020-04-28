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
    @PostMapping("/api/{uid}/stories")
    public ResponseEntity<Story> createStory(@PathVariable("uid") int uid , @RequestBody Story story ){
    User user = storyService.findByIdUser(uid);
    if(user == null){

        return ResponseEntity.notFound().build();

    }
    story.setUser(user);

    return ResponseEntity.ok().body(storyService.save(story));
    }

    @GetMapping("/api/stories")
    public List<Story> getAllStories(){

        System.out.println(storyService.findAll());
        return storyService.findAll();
    }

}
