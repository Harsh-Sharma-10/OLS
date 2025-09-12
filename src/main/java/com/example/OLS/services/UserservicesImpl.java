package com.example.OLS.services;

import com.example.OLS.Model.User;
import com.example.OLS.Repository.Repouser;
import com.example.OLS.Repository.userServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserservicesImpl implements userServices {


    @Autowired
    private Repouser userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder(12);   ///Encryption : plaintext-->hash--->hashverify---->plaintext
    @Override
    public User registerUser(@RequestBody User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username is already taken!");
        }
        user.setId(user.getId());
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(user.getRole() == null){
            user.setRole("USER");
        }
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    @Override
    public User updateUser(Integer id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }


    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
