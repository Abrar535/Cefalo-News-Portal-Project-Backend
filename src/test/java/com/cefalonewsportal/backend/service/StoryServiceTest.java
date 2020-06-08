package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.StoryRepository;
import com.cefalonewsportal.backend.repository.UserRepository;
import com.cefalonewsportal.backend.util.PageContent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
@SpringBootTest
@RunWith(SpringRunner.class)
public class StoryServiceTest {

    @Autowired
    StoryService storyService;
    @Autowired
    PageContent pageContent ;
    @MockBean
    StoryRepository storyRepository;
    @MockBean
    UserService userService;

    User user ;

    @Before
    public void setUp(){

        user = new User(1,"abrar","Abrar Ul Haque","abcd");
        Story story = new Story(2,"Hello Title","Hello Body",new Date(),user);
        Story story1 = new Story(3,"Hello Title","Hello Body",new Date(),user);
        Story story2 = new Story(4,"Hello Title","Hello Body",new Date(),user);

        List<Story> stories = new ArrayList<>();

        stories.add(story);
        stories.add(story1);
        stories.add(story2);




        Page<Story> page = new PageImpl<>(stories);
        //Pageable pageable = PageRequest.of(0,4);
        System.out.println(page.getContent());
        //Mockito.when(storyRepository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(storyRepository.findByUserUserName(any(String.class),any(Pageable.class))).thenReturn(page);
        Mockito.when(storyRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(story));
        Mockito.when(storyRepository.findByStoryIdAndUserUserId(any(Integer.class),any(Integer.class))).thenReturn(story);
        Mockito.when(storyRepository.save(any(Story.class))).thenReturn(story);
        Mockito.doNothing().when(storyRepository).delete(any(Story.class));
        Mockito.when(storyRepository.findByDraftedAndScheduled(any(Boolean.class) ,any(Boolean.class) , any(Pageable.class))).thenReturn(page);
        //Mockito.when(storyRepository.findByDraftedAndScheduled(false ,true)).thenReturn();
    }
    @Test
    public void findByIdTest(){

        int storyId = 2;
        Story story = storyService.findById(storyId);
        assertEquals(story.getStoryId(),2);


    }
    @Test
    public void findByIdAndUserIdTest(){

        int storyId = 2 ,  userId = 1;
        Story story = storyService.findByIdAndUserId(storyId,userId);
        assertEquals(story.getStoryId(),2);


    }
    @Test
    public void findByUserNameTest(){

        int pageNum = 0 , pageSize = 4;
        String userName = "abrar";
        PageContent pageContent = storyService.findByUserName(pageNum,pageSize,userName);
        assertEquals(pageContent.getTotalNumberOfStories(),3);

    }

    @Test
    public void findAllTest(){

        int pageNum = 0 , pageSize = 3;
        PageContent pageContent = storyService.findAll(pageNum,pageSize);
        assertEquals(pageContent.getTotalNumberOfStories(),3);


    }

    @Test
    public void saveTest(){
        Story story = new Story(2,"Hello Title","Hello Body",new Date(),user);
        Story savedStory = storyService.save(story);
        assertEquals(savedStory.getStoryId(),story.getStoryId());



    }

    @Test
    public void deleteStoryTest(){

         storyService.deleteStory(new Story());
        System.out.println("Deleted");
    }
    @Test
    public void checkScheduleTest(){
        Story story3 = new Story("Drafted and scheduled story" , "Drafted and scheduled body", new Date(), user, true ,false, new Date());
        Story story4 = new Story("Drafted and scheduled story" , "Drafted and scheduled body", new Date(), user, true ,false, new Date());
        Story story5 = new Story("Drafted and scheduled story" , "Drafted and scheduled body", new Date(), user, true ,false,new Date());
        List<Story> stories = new ArrayList<>();
        stories.add(story3);
        stories.add(story4);
        stories.add(story5);
        Mockito.when(storyRepository.findByDraftedAndScheduled(any(Boolean.class) ,any(Boolean.class))).thenReturn(stories);
        Mockito.when(storyRepository.save(any(Story.class))).thenReturn(story3);
        storyService.checkSchedule();


    }





}
