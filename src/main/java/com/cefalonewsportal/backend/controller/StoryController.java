package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.StoryService;
import com.cefalonewsportal.backend.service.UserService;
import com.cefalonewsportal.backend.util.JwtUtil;
import com.cefalonewsportal.backend.util.PageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController

public class StoryController {

    @Autowired
    StoryService storyService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;


    /*A user creating a new story*/
    @PostMapping("/api/stories")
    public ResponseEntity<Story> createStory(@RequestBody Story story, @RequestHeader("Authorization") String authorizationHeader ){
    Integer userId = getJwtUserId(authorizationHeader);
    User user = storyService.findByIdUser(userId);
    if(user == null){

        return ResponseEntity.notFound().build();

    }
    story.setUser(user);

    return ResponseEntity.ok().body(storyService.save(story));
    }

    /*Get all stories*/
    @GetMapping("/api/public/stories")
    public PageContent getAllStories(@RequestParam("pageNum") int pageNum , @RequestParam("pageSize") int pageSize){

        return storyService.findAll(pageNum,pageSize);


    }

    /*Get a story by storyId*/

    @GetMapping("/api/public/stories/{storyId}")
    public ResponseEntity<Story> getStory(@PathVariable("storyId") int storyId){
        Story story =storyService.findById(storyId);
        if(story == null){
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(story);


    }


    /*Get all stories of a user_name*/
    @GetMapping("/api/public/{userName}/stories")
    public ResponseEntity< ? > getAllStoriesByUserName(@RequestParam("pageNum") int pageNum , @RequestParam("pageSize") int pageSize, @PathVariable("userName") String userName){
    User user = userService.findByUserName(userName);
    if(user == null){
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserName is Not Found!");

    }
    return ResponseEntity.ok().body(storyService.findByUserName(pageNum,pageSize,userName));

    }

    /*Update a story of a user_id*/
    @PutMapping("/api/stories/{storyId}")
    public ResponseEntity<?> updateStoryByUserId(@PathVariable("storyId") int storyId,@RequestBody Story newStory,@RequestHeader("Authorization") String authorizationHeader ){
        System.out.println("called from update stories");

        Integer userId = getJwtUserId(authorizationHeader);
        Story story = storyService.findByIdAndUserId(storyId,userId);

        if(story == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Story Found to be updated");
        }
        story = storyService.updateStory(story,newStory);
        storyService.save(story);
        return ResponseEntity.ok().body(story);

    }

    /*Delete a story of a user_id*/
    @DeleteMapping("/api/stories/{storyId}")
    public ResponseEntity<?> deleteStoryByUserId(@PathVariable("storyId") int storyId,@RequestHeader("Authorization") String authorizationHeader ){

        Integer userId = getJwtUserId(authorizationHeader);
        Story story = storyService.findByIdAndUserId(storyId,userId);
        if(story == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Such Story Found to be deleted");
        }
        storyService.deleteStory(story);
        return ResponseEntity.ok().body(story);


    }


    public Integer getJwtUserId(final String authorizationHeader){
        String jwt = null , userId = null ;
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUsername(jwt);

        }
        return Integer.parseInt(userId);
    }

}
