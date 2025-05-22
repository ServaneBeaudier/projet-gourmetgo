package fr.gourmetgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  fr.gourmetgo.entity.Utilisateur;
import  fr.gourmetgo.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordService passwordService;

    public boolean authenticate(String email, String motDePasse) {
        Utilisateur user = authRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return passwordService.checkPassword(motDePasse, user.getMotDePasse());
        }
        return false;
    }
    
    public Utilisateur findUser(String email) {
    return authRepository.findByEmail(email).orElse(null);
    }
}