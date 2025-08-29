package com.example.OLS.Controller;

import com.example.OLS.Model.User;
import com.example.OLS.Repository.userServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private userServices userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) {
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = Collections.emptyList();
        users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user){
        User updatedUser = null;
        try{
            updatedUser = userService.getUserById(id).orElse(null);
        }catch(Exception e){
            return new ResponseEntity<>("Bad Request " ,HttpStatus.NOT_FOUND);
        }
        if(updatedUser != null){
            return new ResponseEntity<>((updatedUser), HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to update the user", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        User user1 = userService.getUserById(id).orElse(null);
       if(user1 != null){
           userService.deleteUser(user1.getId());
           return new ResponseEntity<>("Deleted Successfully",HttpStatus.NO_CONTENT);
       }else{
           return new ResponseEntity<>("You wants to delete the user which is already NOT there!!",HttpStatus.BAD_REQUEST);
       }
    }
}
