package com.cefalonewsportal.backend.IntegrationTest;

import com.cefalonewsportal.backend.model.AuthenticationRequest;
import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.StoryRepository;
import com.cefalonewsportal.backend.util.PageContent;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

@TestPropertySource(
        locations = "classpath:application-integration.properties")
public class StoryControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc ;
    @Autowired
    private PageContent pageContent;

    public String jwt ;

    @Before
    public void setUp() throws Exception{
        User user = new User("abrar","Abrar Ul Haque","abcd");
        Story story = new Story(2,"Hello Title","Hello Body",new Date(),user);
        Story story1 = new Story(3,"Hello Title","Hello Body",new Date(),user);
        Story story2 = new Story(4,"Hello Title","Hello Body",new Date(),user);

        List<Story> stories = new ArrayList<>();
        stories.add(story);
        stories.add(story1);
        stories.add(story2);
//        pageContent.setTotalNumberOfStories((long) 3);
//        pageContent.setTotalNumberOfPages(1);
//        pageContent.setStories(stories);


        Gson gson = new Gson();
        String json = gson.toJson(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json));

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("abrar", "abcd");
        json = gson.toJson(authenticationRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/public/authenticate")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json)).andReturn();

        String loginResponse = mvcResult.getResponse().getContentAsString();
        jwt = loginResponse.substring(8,133);
        System.out.println(jwt);

//        for(Story tmpstory : stories){
//            json = gson.toJson(story);
//           mockMvc.perform(MockMvcRequestBuilders.post("/api/stories").header("Authorization","Bearer "+jwt)
//                    .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
//                    .content(json));
//       }


    }

    @Transactional
    @Test
    public void createStoryTestOk() throws Exception{

        Story story = new Story("New Title","New Body");
        Gson gson = new Gson();
        String json = gson.toJson(story);
        // System.out.println(json);
       MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.post("/api/stories").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        System.out.println(mvcResult);


    }

    @Test
    public void getAllStoriesTest() throws Exception{
         mockMvc.perform(MockMvcRequestBuilders.get("/api/public/stories?pageNum=0&pageSize=4")
               .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Transactional
    @Test
    public void updateStoryByUserIdTest() throws  Exception{
        Story story = new Story("New Title","New Body");
        Gson gson = new Gson();
        String json = gson.toJson(story);
        // System.out.println(json);
       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/stories").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        Story newStory = new Story("Updated Title","Updated Body");
        json = gson.toJson(newStory);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/api/stories/4").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());



    }
    @Transactional
    @Test
    public void deleteStoryByUserId() throws  Exception{
        Story story = new Story("New Title","New Body");
        Gson gson = new Gson();
        String json = gson.toJson(story);
        // System.out.println(json);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/stories").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json)).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stories/6").header("Authorization","Bearer "+jwt)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());



    }




}
