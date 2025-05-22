package fr.gourmetgo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.gourmetgo.entity.Client;
import fr.gourmetgo.repository.ClientRepository;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/client-login")
    public String clientLogin(@RequestParam String clientEmail, @RequestParam String clientMotDePasse) {
        Client client = new Client();
        client.setEmail(clientEmail);
        client.setMotDePasse(clientMotDePasse);
        client.setNom(""); // Définissez une valeur par défaut ou récupérez-la depuis le formulaire
        client.setPrenom(""); // Définissez une valeur par défaut ou récupérez-la depuis le formulaire
        clientRepository.save(client); // Sauvegarde le client dans la base de données
        return "redirect:/dashboard";
    }
}