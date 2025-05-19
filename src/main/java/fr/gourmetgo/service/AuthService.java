package fr.gourmetgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  fr.gourmetgo.model.User;
import  fr.gourmetgo.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    public boolean authenticate(String username, String password) {
    return userRepository.findByUsername(username).map(user -> user.getPassword().equals(password)).orElse(false);
    }
    public User findUser(String username) {
    return userRepository.findByUsername(username).orElse(null);
    }
}