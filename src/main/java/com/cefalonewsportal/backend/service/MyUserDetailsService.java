package com.cefalonewsportal.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.cefalonewsportal.backend.model.User;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);
        System.out.println("I am "+user);

        return new org.springframework.security.core.userdetails.User(Integer.toString(user.getUserId()),user.getPassword(),new ArrayList<>());
    }

    public UserDetails loadUserByUsername(Integer userId) throws UsernameNotFoundException {
        User user = userService.findById(userId);
        System.out.println("I am "+user);
        // user.orElseThrow(()-> new UsernameNotFoundException("NOT FOUND" + userName));

        return new org.springframework.security.core.userdetails.User(Integer.toString(user.getUserId()),user.getPassword(),new ArrayList<>());
    }
}
