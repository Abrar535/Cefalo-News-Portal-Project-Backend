package com.cefalonewsportal.backend.controller;


import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.MyUserDetailsService;
import com.cefalonewsportal.backend.service.StoryService;
import com.cefalonewsportal.backend.service.UserService;
import com.cefalonewsportal.backend.util.JwtUtil;
import com.cefalonewsportal.backend.util.PageContent;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPageContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class StoryControllerTest {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PageContent pageContent;
    @MockBean
    private MyUserDetailsService myUserDetailsService;
    @MockBean
    private StoryService storyService;
    public  String jwt ;
    User user;
    @Before
    public void setUp() throws Exception{
        user = new User(1,"abrar","Abrar Ul Haque","abcd");
        Story story = new Story(2,"Hello Title","Hello Body",new Date(),user);
        Story story1 = new Story(3,"Hello Title","Hello Body",new Date(),user);
        Story story2 = new Story(4,"Hello Title","Hello Body",new Date(),user);
        List<Story> stories = new ArrayList<>();
        stories.add(story);
        stories.add(story1);
        stories.add(story2);
        pageContent.setTotalNumberOfStories((long) 3);
        pageContent.setTotalNumberOfPages(1);
        pageContent.setStories(stories);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(Integer.toString(user.getUserId()),user.getPassword(),new ArrayList<>());
        jwt = jwtUtil.generateToken(userDetails);
        Mockito.when(myUserDetailsService.loadUserByUsername(any(Integer.class))).thenReturn(userDetails);
        Mockito.when(storyService.findByIdUser(any(Integer.class))).thenReturn(user);
        Mockito.when(storyService.save(any(Story.class))).thenReturn(story);
        Mockito.when(storyService.findAll(any(Integer.class),any(Integer.class))).thenReturn(pageContent);
        Mockito.when(storyService.findByIdAndUserId(any(Integer.class),any(Integer.class))).thenReturn(story);
        Mockito.when(storyService.save(any(Story.class))).thenReturn(story);
        Mockito.doNothing().when(storyService).deleteStory(any(Story.class));
    }

    @Test
    public void createStoryTestOk() throws Exception {

        Story story = new Story("New Title","New Body");
        Gson gson = new Gson();
        String json = gson.toJson(story);
       // System.out.println(json);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/stories").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void getAllStoriesTest() throws Exception {

         mockMvc.perform(MockMvcRequestBuilders.get("/api/public/stories?pageNum=0&pageSize=4")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stories", Matchers.hasSize(3)));

    }

    @Test
    public void updateStoryByUserIdTest() throws Exception{

        Story story = new Story("Updated Title","Updated Body");
        Gson gson = new Gson();
        String json = gson.toJson(story);
        // System.out.println(json);
        Mockito.when(storyService.updateStory(any(Story.class),any(Story.class))).thenReturn(story);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/stories/2").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void deleteStoryByUserId() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stories/2").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }





}
