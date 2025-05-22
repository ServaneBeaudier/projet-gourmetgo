package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.gourmetgo.entity.Client;
import fr.gourmetgo.repository.ClientRepository;
import fr.gourmetgo.service.PasswordService;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/client-login")
    public String clientLogin(@RequestParam String clientEmail, @RequestParam String clientMotDePasse) {
        Client client = new Client();
        client.setEmail(clientEmail);

        // Utilisez PasswordService pour hasher le mot de passe avant de le sauvegarder
        String hashedPassword = passwordService.hashPassword(clientMotDePasse);
        client.setMotDePasse(hashedPassword);

        client.setNom(""); // Définissez une valeur par défaut ou récupérez-la depuis le formulaire
        client.setPrenom(""); // Définissez une valeur par défaut ou récupérez-la depuis le formulaire
        clientRepository.save(client); // Sauvegarde le client dans la base de données
        return "redirect:/login";
    }
}