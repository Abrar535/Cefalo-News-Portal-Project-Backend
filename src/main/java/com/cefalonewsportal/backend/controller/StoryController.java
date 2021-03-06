package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.FileSystemService;
import com.cefalonewsportal.backend.service.StoryService;
import com.cefalonewsportal.backend.service.TagService;
import com.cefalonewsportal.backend.service.UserService;
import com.cefalonewsportal.backend.util.JwtUtil;
import com.cefalonewsportal.backend.util.PageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@RestController

public class StoryController {

    @Autowired
    StoryService storyService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    FileSystemService fileSystemService;


    /*A user creating a new story*/
    @PostMapping(value = "/api/stories")
    public ResponseEntity<?> createStory(@RequestBody Story story, @RequestHeader("Authorization") String authorizationHeader, @RequestBody MultipartFile image ){
        System.out.println("I am hit from createStory api "+ story.getTags());
        Integer userId = getJwtUserId(authorizationHeader);
    User user = storyService.findByIdUser(userId);
    if(user == null){

        return ResponseEntity.notFound().build();

    }
    if(story.getScheduled()  && story.getScheduledDate() == null){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Scheduled date cannot be empty for a scheduled story");
    }
    story = storyService.addStoryTags(story);
    story.setUser(user);
//    boolean ret = fileSystemService.addImage(image);
//        System.out.println(ret);
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

    /*Get all drafted stories*/
    @GetMapping("/api/stories/drafted")
    public ResponseEntity<?> getDraftedStoriesByUserId(@RequestParam("pageNum") int pageNum , @RequestParam("pageSize") int pageSize,@RequestHeader("Authorization") String authorizationHeader ){
        Integer userId = getJwtUserId(authorizationHeader);
        User user = userService.findById(userId);
        if(user == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UserName is Not Found!");

        }
        return ResponseEntity.ok().body(storyService.findByDraftedAndUserId(pageNum,pageSize,userId));
    }
    /*Get all stories by tags */
    @GetMapping("/api/public/tags/{tagName}/story")
    public ResponseEntity<?> getAllStoriesByTag(@RequestParam("pageNum") int pageNum , @RequestParam("pageSize") int pageSize , @PathVariable ("tagName") String tagName ){
        System.out.println("i am hit "+storyService.getAllStoryByTag(pageNum,pageSize,tagName).getTotalNumberOfStories() );
        return ResponseEntity.ok().body(storyService.getAllStoryByTag(pageNum,pageSize,tagName));


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

    @PutMapping("/api/drafted/stories/{storyId}")
    public ResponseEntity<?> toggleDraftedStoryState(@PathVariable("storyId") int storyId , @RequestHeader("Authorization") String authorizationHeader ){
        Integer userId = getJwtUserId(authorizationHeader);
        Story story = storyService.findByIdAndUserId(storyId,userId);

        if(story == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such Story Found to be updated from drafted");
        }
        return ResponseEntity.ok().body(storyService.toggleDraftedStoryState(story));

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
        String jwt , userId = null ;
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUsername(jwt);

        }
        return Integer.parseInt(userId);
    }
    @Scheduled(fixedDelay = 20000000)
    public void checkSchedule(){
        storyService.checkSchedule();
    }
}
