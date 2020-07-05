package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.Tag;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.StoryRepository;
import com.cefalonewsportal.backend.repository.TagRepository;
import com.cefalonewsportal.backend.util.PageContent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.logging.Logger;

@Service
@CacheConfig(cacheNames = {"stories"})
@Slf4j
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

    @Cacheable(key = "{#storyId}")
    public Story findById(int storyId){
        log.info("Find story by id called");
    return storyRepo.findById(storyId).orElse(null);
    }

    public User findByIdUser(int userId){
        return userService.findById(userId);
    }
    @Cacheable(key = "{#pageNum, #pageSize , #userName}")
    public PageContent findByUserName(int pageNum , int pageSize , String userName)  {


        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Story> page = storyRepo.findByUserUserName(userName,pageable);
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfStories(page.getTotalElements());
        log.info("Find story by user called");
        return pageContent;
    }

    @CacheEvict(allEntries = true)
    public Story save(Story story){
        log.info("Story save called");
        return storyRepo.save(story);
    }
//    get undrafted stories
    @Cacheable(key = "{#pageNum, #pageSize}")
    public PageContent findAll(int pageNum , int pageSize){
        Pageable pageable = PageRequest.of(pageNum,pageSize);
//        System.out.println("I am here");
        Page<Story> page = storyRepo.findByDraftedAndScheduled(false,false , pageable);
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setTotalNumberOfStories( page.getTotalElements());
        log.info("Find All Undrafted stories called");
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
    @Cacheable(key = "{#storyId,#userId}")
    public Story findByIdAndUserId(int storyId , int userId){

        return storyRepo.findByStoryIdAndUserUserId(storyId,userId);

    }
   // @CacheEvict(allEntries = true)
    @Caching(evict = {@CacheEvict(value = "stories", allEntries = true),@CacheEvict(value = "comments" , allEntries = true),@CacheEvict(value = "tags" , allEntries = true)})
    public void deleteStory(Story story){
        log.info("Delete story called");
        storyRepo.delete(story);

    }

    @Cacheable(key = "{#pageNum, #pageSize, #userId}")
    public PageContent findByDraftedAndUserId(int pageNum, int pageSize, Integer userId) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Story> page = storyRepo.findByDraftedAndUserUserId(true , userId,pageable);
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setTotalNumberOfStories( page.getTotalElements());
        log.info("Find drafted stories called");
        return pageContent ;

    }
    //@CacheEvict(allEntries = true)
    //CacheEvict done by save() method.
    public Story toggleDraftedStoryState(Story story){

        story.setDrafted(!story.getDrafted());

        return save(story) ;

    }
    //cached by save method
    public Story addStoryTags(Story story){

        Set<Tag> tags = story.getTags();
        Set<Tag> storedTags = new HashSet<>();

        if(tags == null || tags.size() == 0) {
            tags = new HashSet<>();
            Tag unTagged = new Tag("others");
            tags.add(unTagged);
        }
        Iterator<Tag> it = tags.iterator();
        while(it.hasNext()){
            Tag tag = tagService.createTag(it.next());

            storedTags.add(tag);
        }
        story.setTags(storedTags);

        return story;

    }

    @Cacheable(key = "{#pageNum, #pageSize , #tagName}")
    public PageContent getAllStoryByTag(int pageNum , int pageSize,String tagName){
        tagName = tagName.toLowerCase();
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        Page<Story> page = storyRepo.findAllByTags_TagNameAndDraftedFalseAndScheduledFalse(pageable,tagName);
        pageContent.setStories(page.getContent());
        pageContent.setTotalNumberOfPages(page.getTotalPages());
        pageContent.setTotalNumberOfStories( page.getTotalElements());
        log.info("Find all story by tag called");
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
