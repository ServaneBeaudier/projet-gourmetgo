package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.gourmetgo.entity.Client;
import fr.gourmetgo.repository.ClientRepository;
import fr.gourmetgo.service.PasswordService;

/**
 * Contrôleur pour gérer les opérations liées aux clients.
 * Ce contrôleur gère les requêtes liées à l'inscription des clients.
 */
@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordService passwordService;

    /**
     * Gère l'inscription d'un nouveau client.
     * Cette méthode crée un nouveau client avec l'email et le mot de passe fournis,
     * hache le mot de passe pour des raisons de sécurité, et sauvegarde le client dans la base de données.
     *
     * @param clientEmail L'email du client.
     * @param clientMotDePasse Le mot de passe du client.
     * @return Une redirection vers la page de connexion.
     */
    @PostMapping("/client-login")
    public String clientLogin(@RequestParam String clientEmail, @RequestParam String clientMotDePasse) {
        Client client = new Client();
        client.setEmail(clientEmail);

        // Utilise PasswordService pour hasher le mot de passe avant de le sauvegarder
        String hashedPassword = passwordService.hashPassword(clientMotDePasse);
        client.setMotDePasse(hashedPassword);

        client.setNom(""); // Définit une valeur par défaut
        client.setPrenom(""); // Définit une valeur par défaut
        clientRepository.save(client); // Sauvegarde le client dans la base de données
        return "redirect:/login";
    }
}
