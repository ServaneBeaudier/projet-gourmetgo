package fr.gourmetgo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.gourmetgo.entity.Utilisateur;
import fr.gourmetgo.repository.AuthRepository;

/**
 * Service pour gérer les opérations d'authentification des utilisateurs.
 * Ce service fournit des méthodes pour authentifier les utilisateurs et récupérer les informations des utilisateurs.
 */
@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordService passwordService;

    /**
     * Authentifie un utilisateur avec l'email et le mot de passe fournis.
     *
     * @param email L'email de l'utilisateur.
     * @param motDePasse Le mot de passe de l'utilisateur.
     * @return true si l'authentification est réussie, false sinon.
     */
    public boolean authenticate(String email, String motDePasse) {
        Utilisateur user = authRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return passwordService.checkPassword(motDePasse, user.getMotDePasse());
        }
        return false;
    }

    /**
     * Trouve un utilisateur par son email.
     *
     * @param email L'email de l'utilisateur à rechercher.
     * @return L'utilisateur trouvé, ou null si aucun utilisateur n'est trouvé.
     */
    public Utilisateur findUser(String email) {
        return authRepository.findByEmail(email).orElse(null);
    }
}
