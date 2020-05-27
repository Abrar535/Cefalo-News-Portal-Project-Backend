package com.cefalonewsportal.backend.IntegrationTest;

import com.cefalonewsportal.backend.model.AuthenticationRequest;
import com.cefalonewsportal.backend.model.AuthenticationResponse;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.UserRepository;
import com.cefalonewsportal.backend.util.JwtUtil;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration.properties")
public class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Transactional
    @Test
    public void createUserTestOk() throws Exception{

        User user = new User("abrar","Abrar Ul Haque","abcd");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is(user.getUserName())));
        System.out.println(user.getUserName());

    }
    @Transactional
    @Test
    public void createUserTestConflict() throws Exception {

        User user = new User("abrar","Abrar Ul Haque","abcd");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isConflict());


    }
    @Transactional
    @Test
    public void loginUserTestOk() throws Exception {

        User user = new User("abrar","Abrar Ul Haque","abcd");

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("abrar", "abcd");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json));

        json = gson.toJson(authenticationRequest);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/public/authenticate")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String loginResponse = mvcResult.getResponse().getContentAsString();
        System.out.println(loginResponse);
       // AuthenticationResponse authenticationResponse = gson.fromJson(loginResponse,AuthenticationResponse.class);
        //System.out.println(authenticationResponse.getJwt());
        //assertEquals(jwtUtil.extractUsername(authenticationResponse.getJwt()),"1");
    }


}
