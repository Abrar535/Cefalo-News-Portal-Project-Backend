package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.AuthenticationRequest;
import com.cefalonewsportal.backend.model.AuthenticationResponse;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.MyUserDetailsService;
import com.cefalonewsportal.backend.service.UserService;
import com.cefalonewsportal.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping("/api/users")
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    /*Authenticate a user*/
    @PostMapping("/api/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch(BadCredentialsException e){
            throw new Exception("Incorrect username or password",e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    /*to post a user (USER REGISTRATION)*/
    @PostMapping("/api/register/users")
    public ResponseEntity<?> createUser( @RequestBody User user){
        User tmpUser = userService.findByUserName(user.getUserName());
        System.out.println("I am called from registration " + tmpUser);
        if( tmpUser != null){
            return ResponseEntity.status(409).body("UserName already Exists! Please select a unique UserName.");
        }

        return ResponseEntity.ok().body(userService.save(user));

    }
   /*to get all users*/
   @GetMapping("/api/users")
   public List<User> getAllUsers(){

        return userService.findAll();

   }
   /*Get a user by id */
    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){
        User user = userService.findById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);

    }

    /*to delete a user*/
   @DeleteMapping("/api/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id){

       User user = userService.findById(id);
       if(user == null){
           return ResponseEntity.notFound().build();
       }

       userService.delete(user);
       return ResponseEntity.ok().body(user);

   }
   /*to update a user*/
    @PutMapping("/api/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User updatedUser){
        if(updatedUser.getFullName() == null || updatedUser.getPassword() == null){
            return ResponseEntity.badRequest().build();
        }
        User user = userService.findById(id);
        if(user == null){
           return ResponseEntity.notFound().build();
        }
        user = userService.update(user , updatedUser);
        userService.save(user);
        return ResponseEntity.ok().body(user);

    }




}
