package com.cefalonewsportal.backend.repository;


import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.util.PageContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StoryRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    StoryRepository storyRepository;

    @Before
    public void setUp(){
        User user = new User("abrar","Abrar Ul Haque","abcd");
        Story story = new Story("Hello Title","Hello Body",new Date(),user);
        Story story1 = new Story("Hello Title","Hello Body",new Date(),user);
        Story story2 = new Story("Hello Title","Hello Body",new Date(),user);
        entityManager.persist(user);
        entityManager.persist(story);
        entityManager.persist(story1);
        entityManager.persist(story2);
        entityManager.flush();
    }
    @Test
    public void findAllTest(){
        Pageable pageable =  PageRequest.of(0,10);
        Page page  =storyRepository.findAll(pageable);
        assertEquals(page.getContent().size(),3);
    }

    @Test
    public void findByUserUserIdTest(){
        List<Story> stories =storyRepository.findByUserUserId(1);
        assertEquals(stories.size(),3);

    }

    @Test
    public void findByIdAndUserIdTest(){
        int userId = 1, storyId = 2;
        Story story = storyRepository.findByStoryIdAndUserUserId(storyId,userId);
        assertEquals(story.getStoryId(),2);
        assertEquals(story.getUser().getUserId(),1);
    }


}
