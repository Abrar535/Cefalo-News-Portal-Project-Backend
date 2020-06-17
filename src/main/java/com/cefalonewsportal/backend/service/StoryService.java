package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.Tag;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.StoryRepository;
import com.cefalonewsportal.backend.repository.TagRepository;
import com.cefalonewsportal.backend.util.PageContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;

@Service

public class StoryService {


    @Autowired
    StoryRepository storyRepo;
    @Autowired
    UserService userService;
    @Autowired
    PageContent pageContent;
    @Autowired
    TagService tagService;
    @Autowired
    TagRepository tagRepository;
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
//    get undrafted stories
    public PageContent findAll(int pageNum , int pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
//        System.out.println("I am here");
        Page<Story> page = storyRepo.findByDraftedAndScheduled(false,false , pageable);
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


    public PageContent findByDraftedAndUserId(int pageNum, int pageSize, Integer userId) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Story> page = storyRepo.findByDraftedAndUserUserId(true , userId,pageable);
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setTotalNumberOfStories( page.getTotalElements());
        return pageContent ;

    }
    public Story toggleDraftedStoryState(Story story){

        story.setDrafted(!story.getDrafted());

        return save(story) ;

    }
    public Story addStoryTags(Story story){

        Set<Tag> tags = story.getTags();
        Set<Tag> storedTags = new HashSet<>();
        Iterator<Tag> it = tags.iterator();
        while(it.hasNext()){
            Tag tag = tagService.createTag(it.next());

            storedTags.add(tag);
        }
        story.setTags(storedTags);

        return story;

    }

    public PageContent getAllStoryByTag(int pageNum , int pageSize,String tagName){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Story> page = storyRepo.findAllByTags_TagNameAndDraftedFalseAndScheduledFalse(pageable,tagName);
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setTotalNumberOfStories( page.getTotalElements());
        return pageContent ;

    }


    public void checkSchedule(){
        //System.out.println("called");
        List<Story> stories = storyRepo.findByDraftedAndScheduled(false , true);

        for(Story story:stories){
            System.out.println(story.getScheduledDate());
            if(story.getScheduledDate().before(new Date())){

                story.setPublishedDate(new Date());
                story.setScheduled(false);
                storyRepo.save(story);
                System.out.println(story);
            }

        }


    }


}
