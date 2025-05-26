package fr.gourmetgo.service;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Service pour gérer les opérations de hachage et de vérification des mots de passe.
 * Ce service utilise BCrypt pour hacher les mots de passe et vérifier leur validité.
 */
@Service
public class PasswordService {

    /**
     * Hache un mot de passe en texte clair.
     *
     * @param plainTextPassword Le mot de passe en texte clair à hacher.
     * @return Le mot de passe haché.
     */
    public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * Vérifie si un mot de passe en texte clair correspond à un mot de passe haché.
     *
     * @param plainTextPassword Le mot de passe en texte clair à vérifier.
     * @param hashedPassword Le mot de passe haché à comparer.
     * @return true si le mot de passe en texte clair correspond au mot de passe haché, false sinon.
     */
    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
