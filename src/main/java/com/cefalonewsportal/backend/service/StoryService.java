package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class StoryService {


    @Autowired
    StoryRepository storyRepo;
    @Autowired
    UserService userService;
    /*Post a story*/

    public Story findById(int storyId){
        return storyRepo.findById(storyId).orElse(null);
    }

    public User findByIdUser(int userId){
        return userService.findById(userId);
    }

    public Story save(Story story){
        return storyRepo.save(story);

    }
    public List<Story> findAll(){

        //System.out.println(storyRepo.findAll());
        return storyRepo.findAll();

    }

    public List<Story> findByUserId(int userId){

        //System.out.println(storyRepo.findAll());
        return storyRepo.findByUserUserId(userId);

    }
    public Story updateStory(Story story, Story newStory){
        story.setTitle(newStory.getTitle());
        story.setBody(newStory.getBody());
        return story ;
    }




}
