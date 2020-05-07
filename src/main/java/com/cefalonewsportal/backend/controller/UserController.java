package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.AuthenticationRequest;
import com.cefalonewsportal.backend.model.AuthenticationResponse;
import com.cefalonewsportal.backend.model.User;
import com.cefalonewsportal.backend.service.MyUserDetailsService;
import com.cefalonewsportal.backend.service.UserService;
import com.cefalonewsportal.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/api/public/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest){
        System.out.println("I am being hit");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );

        }
        catch(Exception e){

            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied :UserName Or PassWord Is InCorrect");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }


    /*to post a user (USER REGISTRATION)*/
    @PostMapping("/api/public/register/users")
    public ResponseEntity<?> createUser( @RequestBody User user){
        User tmpUser = userService.findByUserName(user.getUserName());
        System.out.println("I am called from registration " + tmpUser);
        if( tmpUser != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("UserName already Exists! Please select a unique UserName.");
        }

        return ResponseEntity.ok().body(userService.save(user));

    }
   /*to get all users*/
   @GetMapping("/api/public/users")
   public List<User> getAllUsers(){

        return userService.findAll();

   }
   /*Get a user by id */
    @GetMapping("/api/public/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") int id){
        User user = userService.findById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);

    }

    /*to delete a user*/
   @DeleteMapping("/api/public/users/{id}")
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

    /*to get a logged in user*/
    @GetMapping("/api/users/user")
    public  ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorizationHeader) {
        int userId = getJwtUserId(authorizationHeader);
        User user = userService.findById(userId);
        if(user == null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found!");
        }
        return ResponseEntity.ok().body(user);


    }


    public Integer getJwtUserId(final String authorizationHeader){
        String jwt = null , userId = null ;
        if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer")){
            jwt = authorizationHeader.substring(7);
            userId = jwtTokenUtil.extractUsername(jwt);

        }
        return Integer.parseInt(userId);
    }







}
