package fr.gourmetgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  fr.gourmetgo.entity.Utilisateur;
import  fr.gourmetgo.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;
    public boolean authenticate(String email, String motDePasse) {
    return authRepository.findByEmail(email).map(user -> user.getMotDePasse().equals(motDePasse)).orElse(false);
    }
    public Utilisateur findUser(String email) {
    return authRepository.findByEmail(email).orElse(null);
    }
}