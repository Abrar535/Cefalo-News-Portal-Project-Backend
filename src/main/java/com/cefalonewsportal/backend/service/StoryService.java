package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.StoryRepository;
import com.cefalonewsportal.backend.util.PageContent;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class StoryService {


    @Autowired
    StoryRepository storyRepo;
    @Autowired
    UserService userService;
    @Autowired
    PageContent pageContent;
    /*Post a story*/

    public Story findById(int storyId){
        return storyRepo.findById(storyId).orElse(null);
    }

    public User findByIdUser(int userId){
        return userService.findById(userId);
    }
    public PageContent findByUserName(int pageNum , int pageSize , String userName)  {


        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Story> page = storyRepo.findByUserUserName(userName,pageable);
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfStories(page.getTotalElements());
        return pageContent;
    }
    public Story save(Story story){
        return storyRepo.save(story);

    }
    public PageContent findAll(int pageNum , int pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);

        Page<Story> page = storyRepo.findAll(pageable);
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setTotalNumberOfStories( page.getTotalElements());
        return pageContent ;
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
    /*Delete a story*/

    public Story findByIdAndUserId(int storyId , int userId){

        return storyRepo.findByStoryIdAndUserUserId(storyId,userId);

    }

    public void deleteStory(Story story){

        storyRepo.delete(story);

    }




}
