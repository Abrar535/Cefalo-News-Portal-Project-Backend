package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.AuthenticationRequest;
import com.cefalonewsportal.backend.model.AuthenticationResponse;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.UserRepository;
import com.cefalonewsportal.backend.service.MyUserDetailsService;
import com.cefalonewsportal.backend.service.UserService;
import com.cefalonewsportal.backend.util.JwtUtil;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UserControllerTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;
    @MockBean
    private UserService userService;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    MyUserDetailsService myUserDetailsService;

    @Before
    public void setUp(){

        User user = new User("abrar","Abrar Ul Haque","abcd");

        Mockito.when(userService.findByUserName(any(String.class))).thenReturn(null);
        Mockito.when(userService.save(any(User.class))).thenReturn(user);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
          }
    @Test
    public void createUserTestOk() throws Exception {

        User user = new User("abrar","Abrar Ul Haque","abcd");
        Gson gson = new Gson();
        String json = gson.toJson(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", Matchers.is(user.getUserName())));

//       String userResponse = mvcResult.getResponse().getContentAsString();
//       //System.out.println(userResponse);
//       //System.out.println(gson.fromJson(userResponse,User.class));
//        User user1 = gson.fromJson(userResponse,User.class);
//        assertEquals(user1.getUserName(),user.getUserName());

    }
    @Test
    public void createUserTestConflict() throws Exception {

        User user = new User("abrar","Abrar Ul Haque","abcd");
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Mockito.when(userService.findByUserName(any(String.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/public/register/users")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isConflict());


    }

    @Test
    public void loginUserTestOk() throws Exception {

        User user = new User("Abrar535","Abrar Ul Haque","abcd");
        UserDetails userDetails = new org.springframework.security.core.userdetails.User("1",user.getPassword(),new ArrayList<>());

        Mockito.when(myUserDetailsService.loadUserByUsername(any(String.class))).thenReturn(userDetails);

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("Abrar535", "abcd");
        Gson gson = new Gson();
        String json = gson.toJson(authenticationRequest);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/public/authenticate")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String loginResponse = mvcResult.getResponse().getContentAsString();
        AuthenticationResponse authenticationResponse = gson.fromJson(loginResponse,AuthenticationResponse.class);
        //System.out.println(authenticationResponse.getJwt());
        assertEquals(jwtUtil.extractUsername(authenticationResponse.getJwt()),"1");
    }




}
