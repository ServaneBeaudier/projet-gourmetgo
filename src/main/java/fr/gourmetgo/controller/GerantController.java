package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.gourmetgo.entity.Gerant;
import fr.gourmetgo.repository.GerantRepository;
import fr.gourmetgo.service.PasswordService;

/**
 * Contrôleur pour gérer les opérations liées aux gérants.
 * Ce contrôleur gère les requêtes liées à l'inscription des gérants.
 */
@Controller
public class GerantController {

    @Autowired
    private GerantRepository gerantRepository;

    @Autowired
    private PasswordService passwordService; // Injecte PasswordService pour hasher les mots de passe

    /**
     * Gère l'inscription d'un nouveau gérant.
     * Cette méthode crée un nouveau gérant avec l'email et le mot de passe fournis,
     * hache le mot de passe pour des raisons de sécurité, et sauvegarde le gérant dans la base de données.
     *
     * @param gerantEmail L'email du gérant.
     * @param gerantMotDePasse Le mot de passe du gérant.
     * @return Une redirection vers la page de connexion.
     */
    @PostMapping("/gerant-login")
    public String gerantLogin(@RequestParam String gerantEmail, @RequestParam String gerantMotDePasse) {
        Gerant gerant = new Gerant();
        gerant.setEmail(gerantEmail);

        // Utilise PasswordService pour hasher le mot de passe avant de le sauvegarder
        String hashedPassword = passwordService.hashPassword(gerantMotDePasse);
        gerant.setMotDePasse(hashedPassword);

        gerant.setNom(""); // Définit une valeur par défaut
        gerant.setPrenom(""); // Définit une valeur par défaut
        gerantRepository.save(gerant); // Sauvegarde le gérant dans la base de données
        return "redirect:/login";
    }
}
