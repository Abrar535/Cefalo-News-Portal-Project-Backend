package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /*to post a user*/
    @PostMapping("/api/users")
    public User createUser( @RequestBody User user){

        return userService.save(user);

    }
   /*to get all users*/
   @GetMapping("/api/users")
   public List<User> getAllUsers(){

        return userService.findAll();

   }

   @DeleteMapping("/api/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id){

       User user = userService.findById(id);
       if(user == null){
           return ResponseEntity.notFound().build();
       }

       userService.delete(user);
       return ResponseEntity.ok().body(user);

   }

}
