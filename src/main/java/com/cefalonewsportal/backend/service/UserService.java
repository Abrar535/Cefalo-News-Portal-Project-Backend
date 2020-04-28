package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    /*Post a User*/
    public User save(User user){
        return userRepo.save(user);
    }
    /*Get all users*/
    public List<User>findAll(){
        return userRepo.findAll();
    }
    /*find a user*/
    public User findById(int id){
        return userRepo.findById(id).orElse(null);
    }

    /*Delete a user*/
    public void delete(User user){

        userRepo.delete(user);

    }
}
